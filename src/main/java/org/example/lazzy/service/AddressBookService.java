package org.example.lazzy.service;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.lazzy.pojo.AddressBook;

import java.util.List;

public interface AddressBookService {
    /**
     * insert one address
     */
    void addOne(AddressBook addressBook);

    /**
     *  query all the address book infomation for a specific user
     */
    List<AddressBook> selectAll(Long userId);

    /**
     *  query the default address
     * @param addressBook
     * @return
     */
    AddressBook selectByIsDefault(AddressBook addressBook);


    /**
     *  update the value for is_default
     *  the return value is the affected rows
     */
    int updateIsFault(AddressBook addressBook, Long id, int isDefault);

    /**
     * select by Id
     */
    AddressBook selectById(Long id);

    /**
     *  update the address book info
     * @param addressBook
     */
    void updateByAddressBook(AddressBook addressBook);

    /**
     * delete one address book by id
     */
    void deleteById(Long id);
}
