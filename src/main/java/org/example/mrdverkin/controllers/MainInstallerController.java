package org.example.mrdverkin.controllers;

import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Mapping.OrderAttribute;
import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dto.DateAvailability;
import org.example.mrdverkin.dto.SelectInstaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home/mainInstaller")
public class MainInstallerController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InstallerRepository installerRepository;

    @ModelAttribute("selectInstaller")
    public SelectInstaller selectInstaller() {
        return new SelectInstaller();
    }

    @PostMapping()
    public String addInstaller(@RequestBody SelectInstaller selectInstaller) {
        orderRepository.updateInstaller(installerRepository.findByName(selectInstaller.getInstallerFullName()),selectInstaller.getOrderId());
        return "redirect:/home/mainInstaller";
    }


    @GetMapping
    public String mainInstaller(Model model) {
        List<Order> ordes = orderRepository.findByInstallerNull();
        List<DateAvailability> availabilityList = DateAvailability.fromDates(orderRepository);

        List<OrderAttribute> orderAttributes = OrderAttribute.fromOrderList(ordes);
        model.addAttribute("orders", orderAttributes);
        model.addAttribute("installers", installerRepository.findAll());
        model.addAttribute("availabilityList", availabilityList);
        return "mainInstaller";
    }
}
