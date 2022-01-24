package com.thangnd.fastfoodstore.controller;

import com.thangnd.fastfoodstore.entity.user.User;
import com.thangnd.fastfoodstore.utils.Utils;
import com.thangnd.fastfoodstore.entity.food.*;
import com.thangnd.fastfoodstore.service.FoodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/food")
public class FoodController {
    @Value("${upload.path}")
    private String fileUpload;

    @Autowired
    private FoodServiceImpl foodService;

    @GetMapping
    public String showAllFood(@RequestParam("categoryID") Optional<String> category,
                              @RequestParam("key") Optional<String> key,
                              @RequestParam("sortField") Optional<String> sortField,
                              @RequestParam("sortDirection") Optional<String> sortDirection,
                              Model model) {
        if (!key.isPresent()) {
            key = Optional.of("");
        }
        if (!sortField.isPresent()) {
            sortField = Optional.of("soldQuantity");
        }
        if (!sortDirection.isPresent()) {
            sortDirection = Optional.of(Utils.DESC_SORT);
        }
        List<List<Food>> foodPages = new ArrayList<>();
        if (!category.isPresent()) {
            foodPages = foodService.getAllFood(key.get(), sortField.get(), sortDirection.get());
            model.addAttribute("categoryID", "0");
        } else {
            if (category.get().equals("0")) {
                foodPages = foodService.getAllFood(key.get(), sortField.get(), sortDirection.get());
            } else {
                foodPages = foodService.getFoodByCategory(Integer.parseInt(category.get()), key.get(), sortField.get(), sortDirection.get());
            }
            model.addAttribute("categoryID", category.get());
        }

        model.addAttribute("foodPages", foodPages);
        model.addAttribute("key", key.get());
        model.addAttribute("sortField", sortField.get());
        model.addAttribute("sortDirection", sortDirection.get());
        model.addAttribute("reverseSortDirection", sortDirection.get().equals(Utils.DESC_SORT) ? Utils.ASC_SORT : Utils.DESC_SORT);

        List<Category> categoryList = foodService.getAllCategory();
        model.addAttribute("categoryList", categoryList);
        return "food";
    }

    @GetMapping("/{id}")
    public String showFoodDetail(@PathVariable("id") int foodID, Model model, HttpSession session) {
        Food food = foodService.getFoodByID(foodID);
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole().name().equals("Customer")) {
            if (food.getCategory().getID() != Utils.COMBO_TYPE) {
                SingleFood singleFood = foodService.getSingleFoodByID(foodID);
                model.addAttribute("singleFood", singleFood);
                return "food-detail";
            } else {
                ComboFood comboFood = foodService.getComboFoodByID(foodID);
                model.addAttribute("comboFood", comboFood);
                return "combo-food-detail";
            }
        }
        if (user != null && user.getRole().name().equals("Staff")) {
            if (food.getCategory().getID() != Utils.COMBO_TYPE) {
                SingleFood singleFood = foodService.getSingleFoodByID(foodID);
                model.addAttribute("singleFood", singleFood);
                return "staff/food-detail";
            } else {
                ComboFood comboFood = foodService.getComboFoodByID(foodID);
                model.addAttribute("comboFood", comboFood);
                return "staff/combo-food-detail";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/add")
    public String showSingleFoodForm(@ModelAttribute SingleFood singleFood,
                                     Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            model.addAttribute("singleFood", singleFood);
            List<Category> categoryList = foodService.getAllCategory();
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getID() == Utils.COMBO_TYPE) {
                    categoryList.remove(i);
                }
            }
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("page", "add");
            return "staff/single-food-form";
        }
        return "redirect:/login";
    }

    @PostMapping("/add")
    public String addSingleFood(@Valid @ModelAttribute SingleFood singleFood,
                                BindingResult result, Model model,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            if (result.hasErrors()) {
                List<Category> categoryList = foodService.getAllCategory();
                model.addAttribute("categoryList", categoryList);
                model.addAttribute("singleFood", singleFood);
                model.addAttribute("page", "add");
                return "staff/single-food-form";
            } else {
                singleFood = (SingleFood) uploadFile((Food) singleFood);
                singleFood.setCategory(foodService.getCategoryByID(singleFood.getCategory().getID()));
                singleFood.setSoldQuantity(0);
                if (foodService.saveSingleFood(singleFood) != null) {
                    //update category
                    Category category = singleFood.getCategory();
                    category.setFoodQuantity(category.getFoodQuantity() + 1);
                    foodService.updateFoodQuantityOfCategory(category);
                    redirectAttributes.addFlashAttribute("message", "Add food successfully");
                    return "redirect:/dashboard/food";
                } else {
                    List<Category> categoryList = foodService.getAllCategory();
                    for (int i = 0; i < categoryList.size(); i++) {
                        if (categoryList.get(i).getID() == Utils.COMBO_TYPE) {
                            categoryList.remove(i);
                        }
                    }
                    model.addAttribute("categoryList", categoryList);
                    model.addAttribute("singleFood", singleFood);
                    model.addAttribute("page", "add");
                    return "staff/single-food-form";
                }
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/combo/add")
    public String showComboFoodForm(@ModelAttribute ComboFood comboFood, Model model,
                                    RedirectAttributes redirectAttributes,
                                    HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            model.addAttribute("comboFood", comboFood);
            List<SingleFood> singleFoodList = foodService.getAllSingleFood();
            model.addAttribute("singleFoodList", singleFoodList);
            List<Category> categoryList = foodService.getAllCategory();
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getID() == Utils.COMBO_TYPE) {
                    categoryList.remove(i);
                }
            }
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("page", "add");
            return "staff/combo-food-form";
        }
        return "redirect:/login";
    }

    @PostMapping("/combo/add")
    public String addComboFood(@Valid @ModelAttribute ComboFood comboFood,
                               @RequestParam("checkedSingleFood") String checkedSingleFoodStr,
                               @RequestParam("quantity") String quantityStr,
                               BindingResult result, Model model,
                               RedirectAttributes redirectAttributes,
                               HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            if (result.hasErrors()) {
                model.addAttribute("comboFood", comboFood);
                List<SingleFood> singleFoodList = foodService.getAllSingleFood();
                model.addAttribute("singleFoodList", singleFoodList);
                List<Category> categoryList = foodService.getAllCategory();
                for (int i = 0; i < categoryList.size(); i++) {
                    if (categoryList.get(i).getID() == Utils.COMBO_TYPE) {
                        categoryList.remove(i);
                    }
                }
                model.addAttribute("categoryList", categoryList);
                model.addAttribute("page", "add");
                return "staff/combo-food-form";
            } else {
                if (checkedSingleFoodStr != null && quantityStr != null && checkedSingleFoodStr.length() > 0 && quantityStr.length() > 0) {
                    String[] checkedSingleFoodListArr = checkedSingleFoodStr.split(",");
                    String[] quantityArr = quantityStr.split(",");
                    List<ComboLine> comboLineList = new ArrayList<>();
                    for (int i = 0; i < checkedSingleFoodListArr.length; i++) {
                        comboLineList.add(new ComboLine(foodService.getSingleFoodByID(Integer.parseInt(checkedSingleFoodListArr[i])), comboFood, Integer.parseInt(quantityArr[i])));
                    }
                    comboLineList.forEach(comboLine -> System.out.println(comboLine.toString()));
                    comboFood.setComboLineList(comboLineList);
                    comboFood = (ComboFood) uploadFile((Food) comboFood);
                    comboFood.setSoldQuantity(0);

                } else {
                    model.addAttribute("comboFood", comboFood);
                    List<SingleFood> singleFoodList = foodService.getAllSingleFood();
                    model.addAttribute("singleFoodList", singleFoodList);
                    List<Category> categoryList = foodService.getAllCategory();
                    model.addAttribute("categoryList", categoryList);
                    model.addAttribute("page", "add");
                    return "staff/combo-food-form";
                }
                ComboFood comboFood1 = foodService.saveComboFood(comboFood);
                if (comboFood1 != null) {
                    comboFood.getComboLineList().forEach(comboLine -> comboLine.setComboFood(comboFood1));
                    foodService.saveComboFood(comboFood);
                    //update category
                    Category category = comboFood.getCategory();
                    category.setFoodQuantity(category.getFoodQuantity() + 1);
                    foodService.updateFoodQuantityOfCategory(category);

                    String message = "Add successfully!";
                    redirectAttributes.addFlashAttribute("message", message);
                    return "redirect:/dashboard/food";
                } else {
                    model.addAttribute("comboFood", comboFood);
                    List<SingleFood> singleFoodList = foodService.getAllSingleFood();
                    model.addAttribute("singleFoodList", singleFoodList);
                    List<Category> categoryList = foodService.getAllCategory();
                    model.addAttribute("categoryList", categoryList);
                    model.addAttribute("page", "add");
                    return "staff/combo-food-form";
                }
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/edit/{id}")
    public String showFoodEditForm(@PathVariable("id") int id, Model model,
                                   RedirectAttributes redirectAttributes,
                                   HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            Food food = foodService.getFoodByID(id);
            if (food != null) {
                if (food.getCategory().getID() != 1) {
                    SingleFood singleFood = foodService.getSingleFoodByID(food.getID());
                    model.addAttribute("singleFood", singleFood);
                    List<Category> categoryList = foodService.getAllCategory();
                    model.addAttribute("categoryList", categoryList);
                    model.addAttribute("page", "edit");
                    return "staff/single-food-form";
                } else {
                    ComboFood comboFood = foodService.getComboFoodByID(food.getID());
                    model.addAttribute("comboFood", comboFood);
                    model.addAttribute("page", "edit");
                    return "staff/combo-food-form";
                }
            } else {
                String message = "Have errors in processing!";
                redirectAttributes.addFlashAttribute("message", message);
                return "redirect:/dashboard/food";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/edit")
    public String editSingleFood(@Valid @ModelAttribute SingleFood singleFood,
                                 BindingResult result, Model model,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            if (result.hasErrors()) {
                model.addAttribute("singleFood", singleFood);
                List<Category> categoryList = foodService.getAllCategory();
                model.addAttribute("categoryList", categoryList);
                model.addAttribute("page", "edit");
                return "staff/single-food-form";
            } else {
                singleFood = (SingleFood) uploadFile((Food) singleFood);
                singleFood.setCategory(foodService.getCategoryByID(singleFood.getCategory().getID()));
                foodService.editSingleFood(singleFood);
                redirectAttributes.addFlashAttribute("message", "Edit food successfully");
                return "redirect:/dashboard/food";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/combo/edit")
    public String editComboFood(@Valid @ModelAttribute ComboFood comboFood,
                                BindingResult result, Model model,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().name().equals("Staff")) {
            if (result.hasErrors()) {
                model.addAttribute("comboFood", comboFood);
                model.addAttribute("page", "edit");
                return "staff/combo-food-form";
            } else {
                if (foodService.editComboFood(comboFood) != null) {
                    redirectAttributes.addFlashAttribute("message", "Edit Successfully!");
                    return "redirect:/dashboard/food";
                } else {
                    model.addAttribute("comboFood", comboFood);
                    model.addAttribute("page", "edit");
                    return "staff/combo-food-form";
                }
            }
        }
        return "redirect:/login";
    }

//    @GetMapping("/food/delete/{id}")
//    public String deleteFood(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
//        //
//        String message = "";
//        if (foodService.deleteFood(id)) {
//            message = "";
//        } else {
//            message = "";
//        }
//        redirectAttributes.addAttribute("message", message);
//        return "redirect:/dashboard/food";
//    }

    private Object uploadFile(Object object) {
        // handle upload file
        Food food = (Food) object;
        MultipartFile imageFile = food.getImageFile();
        // root file on client
        String fileName = UUID.randomUUID().toString() + imageFile.getOriginalFilename();

        File uploadRootDir = new File(fileUpload);
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdir();
        }
        if (fileName != null && fileName.length() > 0) {
            File serverFile = new File(fileUpload + fileName);
            try {
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(imageFile.getBytes());
                stream.close();
                food.setImage(fileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (Object) food;
    }

}

