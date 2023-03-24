package com.kh.product.web;

import com.kh.product.dao.Product;
import com.kh.product.svc.ProductSVC;
import com.kh.product.web.Form.DetailForm;
import com.kh.product.web.Form.SaveForm;
import com.kh.product.web.Form.UpdateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductSVC productSVC;

  @GetMapping("/add")
  public String saveForm(Model model){
    SaveForm saveForm = new SaveForm();
    model.addAttribute("saveForm", saveForm);
    return "product/saveForm";
  }

  @PostMapping("/add")
  public String save(
      @Valid @ModelAttribute SaveForm saveForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ){
    log.info("saveForm={}",saveForm);


    if(bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "product/saveForm";
    }

    if(saveForm.getQuantity() == 100){
      bindingResult.rejectValue("quantity","product");
    }

    if(saveForm.getQuantity() * saveForm.getPrice() > 100_000_000L){
      bindingResult.reject("totalprice",new String[]{"1000"},"");

    }

    if(saveForm.getQuantity() > 1 && saveForm.getQuantity() < 10){
      bindingResult.reject("quantity", new String[]{"1","2"},"");
    }

    if(bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "product/saveForm";
    }

    Product product = new Product();
    product.setPname(saveForm.getPname());
    product.setQuantity(saveForm.getQuantity());
    product.setPrice(saveForm.getPrice());

    Long savedPid = productSVC.save(product);
    redirectAttributes.addAttribute("id",savedPid);
    return "redirect:/products/{id}/detail";

  }

  @GetMapping("/{id}/detail")
  public String findById(
      @PathVariable("id") Long id,
      Model model
  ){
    Optional<Product> findedProduct = productSVC.findById(id);
    Product product = findedProduct.orElseThrow();

    DetailForm detailForm = new DetailForm();
    detailForm.setPid(product.getPid());
    detailForm.setPname(product.getPname());
    detailForm.setQuantity(product.getQuantity());
    detailForm.setPrice(product.getPrice());

    model.addAttribute("detailForm", detailForm);
    return "product/detailForm";
  }


  @GetMapping("/{id}/edit")
  public String updateForm(
      @PathVariable("id") Long id,
      Model model
  ){
    Optional<Product> findedProduct = productSVC.findById(id);
    Product product = findedProduct.orElseThrow();

    UpdateForm updateForm = new UpdateForm();
    updateForm.setPid(product.getPid());
    updateForm.setPname(product.getPname());
    updateForm.setQuantity(product.getQuantity());
    updateForm.setPrice(product.getPrice());

    model.addAttribute("updateForm", updateForm);
    return "product/updateForm";
  }

  @PostMapping("/{id}/edit")
  public String update(
      @PathVariable("id") Long pid,
      @Valid @ModelAttribute UpdateForm updateForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ){

    if(bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "product/updateForm";
    }

    Product product = new Product();
    product.setPid(pid);
    product.setPname(updateForm.getPname());
    product.setQuantity(updateForm.getQuantity());
    product.setPrice(updateForm.getPrice());

    productSVC.update(pid, product);

    redirectAttributes.addAttribute("id",pid);
    return "redirect:/products/{id}/detail";
  }


  @GetMapping("/{id}/del")
  public String deleteById(@PathVariable("id") Long pid){

    productSVC.delete(pid);

    return "redirect:/products";

  }


  @GetMapping
  public String findAll(Model model){

    List<Product> products = productSVC.findAll();
    model.addAttribute("products", products);

    return "product/all";

  }

}
