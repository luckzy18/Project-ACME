package Test;
import org.example.Database.DBinterface;
import org.example.model.Account.Overdraft;
import org.example.model.people.Customer;
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

    @Test
    void testCalculateMonthlyInterest_WhenInOverdraft() {
        Overdraft overdraft = new Overdraft();
        overdraft.setOverdraftBalance(-200); // £200 overdraft

        double interest = overdraft.calculateMonthlyInterest();

        // 1.5% of 200 = 3.0
        Assert.assertEquals(3.0, interest, 0.0001);
    }

    @Test
    void testCalculateMonthlyInterest_WhenNotInOverdraft() {
        Overdraft overdraft = new Overdraft();
        overdraft.setOverdraftBalance(100); // positive balance

        double interest = overdraft.calculateMonthlyInterest();

        Assert.assertEquals(0.0, interest, 0.0001);
    }

    @Test
    void testCalculateMonthlyInterest_WhenZeroBalance() {
        Overdraft overdraft = new Overdraft();
        overdraft.setOverdraftBalance(0);

        double interest = overdraft.calculateMonthlyInterest();

        Assert.assertEquals(0.0, interest, 0.0001);
    }

}
