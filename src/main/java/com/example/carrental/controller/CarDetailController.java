package com.example.carrental.controller;

import com.example.carrental.entity.Car;
import com.example.carrental.entity.Image;
import com.example.carrental.service.CarDetailService;
import com.example.carrental.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CarDetailController {

    private final CarDetailService carDetailService;
    private final CarService carService;

    @GetMapping("/car-detail/add")
    public String carDetailAddPage() {
        return "car-detail";
    }

    /**
     * This method add car images
     */
    @PostMapping("/car-detail/add")
    public String carDetailAdd(@RequestParam("carId") int carId,
                               @RequestParam("carImage") MultipartFile[] files) {
        carDetailService.save(carId, files);
        return "redirect:/cars/detail?id=" + carId;
    }

    @GetMapping(value = "/cars/detail/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) {
        return carDetailService.getCarService(fileName);
    }

    /**
     * This method show car and images of car
     */
    @GetMapping("/cars/detail")
    public String carDetailPage(@RequestParam("id") int id,
                                ModelMap modelMap) {
        Optional<Car> byId = carService.findById(id);
        byId.ifPresent(car -> modelMap.addAttribute("car", car));
        List<Image> all = carDetailService.findAllByCar(id);
        modelMap.addAttribute("images", all);
        return "car-detail";
    }

    @GetMapping("/cars/detail/remove")
    public String deleteCarDetail(@RequestParam("id") int id) {
        carDetailService.delete(id);
        return "redirect:/cars";
    }

}