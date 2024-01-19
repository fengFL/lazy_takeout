package org.example.lazzy.mapper;

import org.apache.ibatis.annotations.*;
import org.example.lazzy.pojo.AddressBook;

import java.util.List;

/**
 *  used to operate the address_book database
 */
@Mapper
public interface AddressBookMapper {
    /**
     *  add address
     */
    @Insert("insert into address_book (id, user_id, consignee, sex, phone, state_code, state_name, city_code, city_name, county_code, " +
            "county_name, detail, label, is_default, is_deleted, create_time, create_user, update_time, update_user) values (#{id}, #{userId}, #{consignee}, #{sex}, #{phone}, #{stateCode}, #{stateName}, #{cityCode}, #{cityName}, #{countyCode}, " +
            " #{countyName}, #{detail}, #{label}, #{isDefault}, #{isDeleted}, #{createTime}, #{createUser}, #{updateTime}, #{updateUser})")
    void insertOne(AddressBook addressBook);

    /**
     *  query all the address book information by user_id
     */
    @Select("select * from address_book where user_id = #{userId} order by create_time ASC")
    List<AddressBook> selectAll(Long userId);

    /**
     *  query the default address
     * @param addressBook
     * @return
     */
    @Select("select * from address_book where is_default = #{isDefault};")
    AddressBook selectByIsDefault(AddressBook addressBook);

    /**
     *  update the value for is_default
     *  the return value is the affected rows
     */
    @Update("update address_book set is_default = #{isDefault} where id = #{id}")
    int updateIsFault(@Param("id") Long id, @Param("isDefault") int isDefault);

    /**
     * select by Id
     */
    @Select("select * from address_book where id = #{id};")
    AddressBook selectById(Long id);


    /**
     *  update address book info by address book object
     *  using dynamic sql
     * @param addressBook
     */
    void updateByAddressBook(AddressBook addressBook);

    /**
     *  delete one address book by id
     */
    @Delete("delete from address_book where id = #{id};")
    void deleteById(Long id);
}
