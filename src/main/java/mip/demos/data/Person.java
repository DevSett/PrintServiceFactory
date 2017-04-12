package mip.demos.data;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by killsett on 12.04.17.
 */
public class Person {
    private boolean sex;
    private String emailAddress;
    private Integer age;
    private String name;

    public Person(boolean sex, String emailAddress, Integer age, String name) {
        this.sex = sex;
        this.emailAddress = emailAddress;
        this.age = age;
        this.name = name;
    }

    public Person(boolean sex, String emailAddress, String name) {
        this.sex = sex;
        this.emailAddress = emailAddress;
        this.name = name;
    }

    public Person() {
        //nothing
    }

    public static List<Person> createRoster() {
        List<Person> people = new ArrayList<>();
        people.add(new Person(true, "killsett", 32, "Сергей"));
        people.add(new Person(true, "Vasy", 31, "Иван"));
        people.add(new Person(true, "Dron", 42, "Георгий"));
        return people;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
