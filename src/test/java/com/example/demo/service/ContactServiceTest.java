package com.example.demo.service;

import com.example.demo.entity.Contact;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContactServiceTest {

    private ContactService contactManager;

    @BeforeAll
    public static void setupAll() {
        System.out.println("Should Print Before All Tests");
    }

    @BeforeEach
    public void setup() {
        System.out.println("Instantiating Contact Manager");
        contactManager = new ContactService();
    }

    @Test
    @DisplayName("Should Create Contact")
    public void shouldCreateContact() {
        contactManager.addContact("John", "Doe", "0546094499");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @Test
    @DisplayName("Should Not Create Contact When First Name is Null")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact(null, "Doe", "0546094499");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Last Name is Null")
    public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", null, "0546094499");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Phone Number is Null")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", "Doe", null);
        });
    }

    @Test
    @DisplayName("Should Create Contact")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Should Run only on MAC")
    public void shouldCreateContactOnMAC() {
        contactManager.addContact("John", "Doe", "0546094499");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @Test
    @DisplayName("Phone Number should start with 0")
    public void shouldTestPhoneNumberFormat() {
        contactManager.addContact("John", "Doe", "0546094499");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }


    @Nested
    class RepeatedTests {
        @DisplayName("Repeat Contact Creation Test 5 Times")
        @RepeatedTest(value = 5,
                name = "Repeating Contact Creation Test {currentRepetition} of {totalRepetitions}")
        public void shouldTestContactCreationRepeatedly() {
            contactManager.addContact("John", "Doe", "0123456789");
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }
    }

    @DisplayName("Method Source Case - Phone Number should match the required Format")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldTestPhoneNumberFormatUsingMethodSource(String phoneNumber) {
        contactManager.addContact("John", "Doe", phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    private static List<String> phoneNumberList() {
        return Arrays.asList("0546094499", "0209453382", "0271234380");
    }

    @Test
    @DisplayName("Test Should Be Disabled")
    @Disabled
    public void shouldBeDisabled() {
        throw new RuntimeException("Test Should Not be executed");
    }


    @AfterEach
    public void tearDown() {
        System.out.println("Should Execute After Each Test");
    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("Should be executed at the end of the Test");
    }
}