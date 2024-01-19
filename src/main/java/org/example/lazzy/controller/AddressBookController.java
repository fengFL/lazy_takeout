package org.example.lazzy.controller;

import org.example.lazzy.common.BaseContext;
import org.example.lazzy.common.CustomException;
import org.example.lazzy.common.Result;
import org.example.lazzy.pojo.AddressBook;
import org.example.lazzy.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    public Result<String> insertOne(@RequestBody AddressBook addressBook){
        // addressBook is not null check
        if(addressBook == null) {
            throw new CustomException("address book cannot be null");
        }
        if(addressBook.getConsignee() == null || addressBook.getConsignee().equals("")) {
            throw new CustomException("consignee cannot be null");
        }
        if(addressBook.getDetail() == null || addressBook.getDetail().equals("")){
            throw new CustomException("detail cannot be null");
        }
        if(addressBook.getSex() == null || addressBook.getSex().equals("")){
            throw new CustomException("sex cannot be null");
        }

        if(addressBook.getLabel() == null || addressBook.getLabel().equals("")){
            throw new CustomException("label cannot be null");
        }

        if(addressBook.getPhone() == null || addressBook.getPhone().equals("")){
            throw new CustomException("phone cannot be null");
        }

        addressBookService.addOne(addressBook);
        return Result.success("insert address successful");
    }

    /**
     *  query all address book information for a specific user
     */
    @GetMapping("/list")
    public Result<List<AddressBook>> list(){
        Long userId = BaseContext.getCurrentId();
        // query
        List<AddressBook> addressBooks = addressBookService.selectAll(userId);
        if(addressBooks == null) {
            throw new CustomException("addressBook does not exist");
        }
        return Result.success(addressBooks);
    }

    /**
     * update isDefault value
     */
    @PutMapping("/default")
    public Result<String> updateIsDefault(@RequestBody AddressBook addressBook){
        // get the addressBook id
        if(addressBook == null){
            throw new CustomException("address does not exist");
        }
        if(addressBook.getId() == null) {
            throw new CustomException("address id cannot be null");
        }
        // set all the isDefault to 0
        AddressBook addBook = new AddressBook();
        addBook.setIsDefault(0);
        addressBookService.updateByAddressBook(addBook);


       // set current isDefault to 1
        addressBook.setIsDefault(1);
        addressBookService.updateByAddressBook(addressBook);
        return Result.success("Succeed");
    }

    /**
     *  query address book info by id
     */
    @GetMapping("/{id}")
    public Result<AddressBook> selectById(@PathVariable Long id){
        if(id == null) {
            throw new CustomException("Invalid id");
        }
        AddressBook addressBook = addressBookService.selectById(id);
        if(addressBook == null) {
            throw new CustomException("no such address book");
        }
        return Result.success(addressBook);
    }

    /**
     *  update info by Addressbook Object
     *
     */
    @PutMapping
    public Result<String> updateByAddressBook(@RequestBody AddressBook addressBook){
        if(addressBook == null) {
            throw new CustomException("address cannot be null");
        }
        addressBookService.updateByAddressBook(addressBook);
        return Result.success("update succeed");
    }

    /**
     *  delete address book by id
     */
    @DeleteMapping
    public Result<String> deleteById(Long ids){
        if(ids == null) {
            throw new CustomException("no such id");
        }
        addressBookService.deleteById(ids);
        return Result.success("delete successful");
    }

    /**
     * get default address
     */
    @GetMapping("/default")
    public Result<AddressBook> address(){
        AddressBook addressBook = new AddressBook();
        addressBook.setIsDefault(1);

        AddressBook add = addressBookService.selectByIsDefault(addressBook);
        if(add == null) {
            throw new CustomException("no such address");
        }
        return Result.success(add);
    }

}
