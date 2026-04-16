package Test;
import org.example.Database.DBinterface;
import org.example.model.Customer;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InsertTest {

    @Test
    void testGetCustomer() {
        // Insert the customer
//        boolean insert=DBinterface.insertCustomer("Test", true, true);
//        Assert.assertTrue(insert,"failed to insert user");
        // Retrieve the customer from the DB
       Customer customer = DBinterface.getCustomerbyID(1);

        // Assert the values are stored correctly
       Assert.assertNotNull(customer, "Customer should not be null");
       IO.println(customer.getName());
       Assert.assertTrue(customer.getName().equals("Test"));
     //   Assert.assertEquals(customer.getName(), "Test", "Customer name should match");
       // Assert.assertTrue(customer.isActive(), "Customer active flag should be true");
     //   Assert.assertTrue(customer.isVerified(), "Customer verified flag should be true");
    }
    @Test
    void testInsertCustomer() {}

}
