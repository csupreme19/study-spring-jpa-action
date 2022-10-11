package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.model.BookForm;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {

        Book book = Book.builder()
                .id(form.getId())
                .name(form.getName())
                .author(form.getAuthor())
                .isbn(form.getIsbn())
                .stockQuantity(form.getStockQuantity())
                .price(form.getPrice())
                .build();

        itemService.addItem(book);

        return "redirect:/items";
    }


    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(Model model, @PathVariable("itemId") Long id) {
        Book book = (Book)itemService.findItem(id);
        BookForm form = BookForm.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .name(book.getName())
                .price(book.getPrice())
                .stockQuantity(book.getStockQuantity())
                .build();

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItemForm(@ModelAttribute("form") BookForm form, @PathVariable Long itemId) {
        itemService.updateItem(form.getId(), form.getName(), form.getPrice(), form.getIsbn(), form.getAuthor(), form.getStockQuantity());
        return "redirect:/items";
    }

}
