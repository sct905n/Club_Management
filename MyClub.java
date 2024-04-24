// Club Application (WIP)
// ===========================

import java.io.*;
import java.util.*;

public class MyClub {

  public static void main(String[] args) throws Exception {
    ClubApp myClub = new ClubApp();
    Random rand = new Random();
    Scanner file_scanner = new Scanner(new File("member_list.csv"));

    for (int i = 0; i < 20; i++) {
      Member x = new Member();
      x.times_paid = rand.nextInt(50);
      myClub.addMember(x);
      x.first_name = "" + (char) (rand.nextInt(26) + 'a');
    }
    System.out.println(myClub);

    myClub.sortbByTimesPaid();

    System.out.println(myClub);
    myClub.getMemberList();

    file_scanner.close();
  }
}

class ClubApp {
  public ArrayList<Member> member_list;
  public int member_fee;
  public int hall_rent;
  public int coach_salary;

  public ClubApp(int member_fee, int hall_rent, int coach_salary) {
    this.member_list = new ArrayList<Member>();
    this.member_fee = member_fee;
    this.hall_rent = hall_rent;
    this.coach_salary = coach_salary;
  }

  public ClubApp(
    ArrayList<Member> new_list,
    int member_fee,
    int hall_rent,
    int coach_salary
  ) {
    this.member_list = new_list;
    this.member_fee = member_fee;
    this.hall_rent = hall_rent;
    this.coach_salary = coach_salary;
  }

  public ClubApp() {
    this.member_list = new ArrayList<Member>();
    this.member_fee = 10;
    this.hall_rent = 100;
    this.coach_salary = 20;
  }

  public String toString() {
    String output_list = "";
    for (int i = 0; i < member_list.size(); i++) {
      output_list += member_list.get(i) + "\n";
    }
    return output_list;
  }

  public int getSize() {
    return member_list.size();
  }

  public int makeID() {
    return getSize();
  }

  public void addMember(
    String first,
    String last,
    String phone,
    String address,
    int[] monthly,
    int attended,
    int paid,
    boolean discount
  ) {
    member_list.add(
      new Member(
        this.makeID(),
        first,
        last,
        phone,
        address,
        monthly,
        attended,
        paid,
        discount
      )
    );
  }

  public void addMember(Member new_member) {
    new_member.setID(this.makeID());
    member_list.add(new_member);
  }

  public void sortbByTimesPaid() {
    Collections.sort(member_list);
  }

  public int getRevenue() {
    int revenue = 0;
    for (int i = 0; i < this.getSize(); i++) {
      revenue += member_list.get(i).getTimesPaid() * member_fee;
    }
    return revenue;
  }

  public int getExpenses() {
    int expenses = 0;
    expenses += hall_rent;
    return expenses;
  }

  //Leo's taks
  public void getMemberList() {
    System.out.println(this.toString());
  }
}

class Member implements Comparable<Member> {
  public int id;
  public String first_name;
  public String last_name;
  public String phone_number;
  public String address;
  public int[] monthly_attendance;
  public int times_attended;
  public int times_paid;
  public boolean discount;
  public int times_unpaid;

  public Member(
    int id,
    String first,
    String last,
    String phone,
    String address,
    int[] monthly,
    int attended,
    int paid,
    boolean discount
  ) {
    this.id = id;
    this.first_name = first;
    this.last_name = last;
    this.phone_number = phone;
    this.address = address;
    this.monthly_attendance = monthly;
    this.times_attended = attended;
    this.times_paid = paid;
    this.discount = discount;
  }

  public Member() {
    this.id = 0;
    this.first_name = "N/A";
    this.last_name = "N/A";
    this.phone_number = "123-456-7890";
    this.address = "Toronto, ON";
    this.monthly_attendance = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    this.times_attended = 0;
    this.times_paid = 0;
    this.discount = false;
  }

  public String toString() {
    return (
      "ID: " +
      id +
      " | " +
      first_name +
      " " +
      last_name +
      " | " +
      phone_number +
      " | " +
      address +
      " | Times Attended: " +
      times_attended +
      " | Times Paid: " +
      times_paid
    );
  }

  public int getID() {
    return this.id;
  }

  public void setID(int new_id) {
    this.id = new_id;
  }

  public String getFirstName() {
    return this.first_name;
  }

  public void setFirstName(String new_first) {
    this.first_name = new_first;
  }

  public String getLastName() {
    return this.last_name;
  }

  public void setLastName(String new_last) {
    this.last_name = new_last;
  }

  public String getPhoneNumber() {
    return this.phone_number;
  }

  public void setPhoneNumber(String new_phone) {
    this.phone_number = new_phone;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String new_address) {
    this.address = new_address;
  }

  public int[] getMonthlyAttendance() {
    return this.monthly_attendance;
  }

  public void setMonthlyAttendance(int[] new_monthly_attendance) {
    this.monthly_attendance = new_monthly_attendance;
  }

  public int getTimesAttended() {
    return this.times_attended;
  }

  public void setTimesAttended(int new_attended) {
    this.times_attended = new_attended;
  }

  public int getTimesPaid() {
    return this.times_paid;
  }

  public void setTimesPaid(int new_paid) {
    this.times_paid = new_paid;
  }

  public boolean getDiscountStatus() {
    return this.discount;
  }

  public void setDiscountStatus(boolean new_discount_status) {
    this.discount = new_discount_status;
  }

  //Leo's task
  public void setTimesUnpaid(int unpaid) {
    this.times_unpaid = unpaid;
  }

  public void UnpaidReminder() {
    if (this.times_unpaid > 1) {
      System.out.println("Please make your payment!");
    }
  }

  @Override
  public int compareTo(Member member2) {
    return this.times_paid - member2.times_paid;
  }
}
