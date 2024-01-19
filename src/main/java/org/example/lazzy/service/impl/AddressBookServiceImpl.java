package org.example.lazzy.service.impl;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.example.lazzy.common.BaseContext;
import org.example.lazzy.mapper.AddressBookMapper;
import org.example.lazzy.pojo.AddressBook;
import org.example.lazzy.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    public void addOne(AddressBook addressBook) {
        addressBook.setId(System.currentTimeMillis());
        Long currentId = BaseContext.getCurrentId(); // current id is the user id
        addressBook.setUserId(currentId);
        addressBook.setIsDefault(0);
        addressBook.setIsDeleted(0);
        // inset the addressBook into address_book table
        addressBookMapper.insertOne(addressBook);
    }

    /**
     *  query the default address
     * @param addressBook
     * @return
     */
    public AddressBook selectByIsDefault(AddressBook addressBook){
        return addressBookMapper.selectByIsDefault(addressBook);
    }


    /**
     *  query all address book infomation for a specific user
     * @return
     */
    @Override
    public List<AddressBook> selectAll(Long userId) {
        return addressBookMapper.selectAll(userId);

    }

    /**
     *  update the value for is_default
     *  the return value is the affected rows
     */
    @Override
    public int updateIsFault(AddressBook addressBook, Long id, int isDefault){
        return addressBookMapper.updateIsFault(id, isDefault);
    }

    /**
     *  query address book info by ID
     * @param id
     * @return
     */
    @Override
    public AddressBook selectById(Long id) {

        return addressBookMapper.selectById(id);
    }

    /**
     *  updatae address book info by AddressBook object
     * @param addressBook
     */
    @Override
    public void updateByAddressBook(AddressBook addressBook) {
        addressBookMapper.updateByAddressBook(addressBook);
    }

    /**
     *  delete one address book by ID
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        addressBookMapper.deleteById(id);
    }
}
