// Club Application (WIP)
// ===========================

import java.io.*;
import java.util.*;

public class Club {
    public static void main(String []args) throws Exception {
        
        ClubApp myClub = new ClubApp();
        
        // The following reads the member_list CSV file and creates members based on the data
        String line = "";
        String delimiter = ",";
        try {
            BufferedReader member_br = new BufferedReader(new FileReader("member_list.csv"));
            member_br.readLine();  // this will read the 1st line (the row with the titles) and ignores it

            for (int i = 1; i < 25; i++) {  // hardcoding how many lines to read to input into members; in this case, there are only 24 members
                line = member_br.readLine();
                String[] member_data = line.split(delimiter);
                
                // This reads the attendance for each month into an int[] array
                int[] months = new int[12];
                for (int j = 0; j < 12; j++) {
                    months[j] = Integer.parseInt(member_data[j+5]);
                }
                // This reads the discount status -> if there is a "Yes", it will be considered true
                boolean discount_status = false;
                if (member_data[21].contains("Yes")) {
                    discount_status = true;
                }
                // This reads the months unpaid column, with a hardcoded value to start at 
                // (the value is the number after the amount paid column);
                // this also removes any non alphabetical chars (like the "" and the space) surrounding the item
                ArrayList<String> months_unpaid = new ArrayList<>();
                for (int k = 23; k < member_data.length; k++) {
                    months_unpaid.add(member_data[k].replaceAll("[^\\p{IsAlphabetic}]", ""));
                }
                Member current = new Member(Integer.parseInt(member_data[0]), member_data[1], member_data[2], member_data[3], member_data[4], 
                                    months, Integer.parseInt(member_data[17]), Integer.parseInt(member_data[18]), Integer.parseInt(member_data[19]), 
                                    discount_status, new ArrayList<String>(), Integer.parseInt(member_data[22]), months_unpaid);
                
                // manually adding the months of discounts for members
                int m = 0;
                if (current.getID() == 1) {
                    for (m = 5; m <= 12; m++) {
                        current.addDiscountMonth(myClub.numToMonth(m));
                    }
                }
                if (current.getID() == 2) {
                    for (m = 7; m <= 12; m++) {
                        current.addDiscountMonth(myClub.numToMonth(m));
                    }
                }
                if (current.getID() == 4) {
                    for (m = 10; m <= 12; m++) {
                        current.addDiscountMonth(myClub.numToMonth(m));
                    }
                }
                myClub.addMember(current);
            }
            member_br.close();
        }   
        catch (IOException e) {
            e.printStackTrace();
        }

        // The following reads the coach_data CSV file and fills in the coach's info
        try {
            BufferedReader coach_br = new BufferedReader(new FileReader("coach_data.csv"));
            coach_br.readLine();  // this will read the 1st line (the row with the titles) and ignores it
            String[] coach_info = coach_br.readLine().split(delimiter);
            myClub.setCoach(new Coach(coach_info[0], coach_info[1], coach_info[2], coach_info[3], Integer.parseInt(coach_info[4]), 0, true));
            coach_br.readLine();  // this will read the blank line and ignores it
            coach_br.readLine();  // this will read the line with the titles Unattended practices and ignores it

            // The following will read and input the monthly inattendance of the coach
            for (int i = 1; i <= 12; i++) {
                line = coach_br.readLine();
                String[] coach_inattendance = line.split(",");
                myClub.getCoach().setMonthlyInattendance(i, Integer.parseInt(coach_inattendance[1]));
            }
            coach_br.close();
        }   
        catch (IOException e) {
            e.printStackTrace();
        }

        // The following is to test the sort by number of times paid functionality
        System.out.println("\nUnsorted:");
        System.out.println(myClub);
        
        myClub.sortbByTimesPaid();
        
        System.out.println("Sorted:");
        System.out.println(myClub);

        int test_month = 4;
        int test_var_cost = 0;
        myClub.coach.setPaidStatus(false);
        // note that unpaid salary is from the prev month; if coach wasn't paid for the current month, unpaid salary is still 0, but next month can be set to the value
        myClub.coach.setUnpaidSalary(0);
        System.out.println();
        System.out.println(myClub.getExpenses(test_month, test_var_cost));
        System.out.println(myClub.getRevenue(test_month));
        System.out.println(myClub.getProfit(test_month, test_var_cost));
    }
}

class ClubApp {
    public ArrayList<Member> member_list;
    public int member_fee;
    public int hall_rent;
    public Coach coach;

    public ClubApp(int member_fee, int hall_rent) {
        this.member_list = new ArrayList<Member>();
        this.member_fee = member_fee;
        this.hall_rent = hall_rent;
        this.coach = new Coach();
    }
    public ClubApp(ArrayList<Member> new_list, int member_fee, int hall_rent, Coach coach) {
        this.member_list = new_list;
        this.member_fee = member_fee;
        this.hall_rent = hall_rent;
        this.coach = coach;
    }

    public ClubApp() {
        this.member_list = new ArrayList<Member>();
        this.member_fee = 10;
        this.hall_rent = 200;
        this.coach = new Coach();
    }

    public String toString() {
        String output_list = this.coach + "\nMembers:\n";
        for (int i = 0; i < member_list.size(); i++) {
            output_list += member_list.get(i) + "\n";
        }
        return output_list;
    }

    public Coach getCoach() {
        return this.coach;
    }
    public void setCoach(Coach new_coach) {
        this.coach = new_coach;
    }

    public int getSize() {
        return member_list.size();
    }
    public int makeID() {
        return getSize();
    }

    public void addMember(int id, String first, String last, String phone, String address, int[] monthly, int attended, int times_paid, 
                            int times_unpaid, boolean discount, ArrayList<String> discount_months, int amount_paid, ArrayList<String> months_unpaid) {
        member_list.add(new Member(id, first, last, phone, address, monthly, attended, times_paid, times_unpaid, discount,
                    discount_months, amount_paid, months_unpaid));
    }
    public void addMember(Member new_member) {
        new_member.setID(this.makeID());
        member_list.add(new_member);
    }

    public boolean removeMember(int query_id) {
        for (int i = 0; i < member_list.size(); i++) {
            if (member_list.get(i).getID() == query_id) {
                member_list.remove(i);
                return true;
            }
        }
        return false;
    }

    public void sortbByTimesPaid() {
        Collections.sort(member_list);
    }

    public String numToMonth(int month_num) {
        String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        if (month_num < 1 || month_num > 12) {
            return months[0];
        }
        return months[month_num-1];
    }

    public int getNumMembersUnpaid(int month_num) {
        int count = 0;
        for (int i = 0; i < member_list.size(); i++) {
            if (member_list.get(i).getMonthsUnpaid().contains(this.numToMonth(month_num))) {
                count += 1;
            }
        }
        return count;
    }

    public int getRevenue(int month_num) {
        if (1 <= month_num && month_num <= 12) {
            int total_attendance = 0;
            int prev_month_unpaid_count = 0;
            int curr_month_unpaid_count = 0;
            int discount_num = 0;
            for (int i = 0; i < member_list.size(); i++) {
                Member current = member_list.get(i);
                total_attendance += current.getMonthlyAttendance()[month_num-1];
                
                // adds up the amount of people who didn't pay last month, so they have to pay it back this month
                if (month_num != 1) {
                    if (current.getMonthsUnpaid().contains(this.numToMonth(month_num-1))) {
                        prev_month_unpaid_count += 1;
                    }
                }
                // adds up the amount of people who didn't pay this month
                if (current.getMonthsUnpaid().contains(this.numToMonth(month_num))) {
                    curr_month_unpaid_count += 1;
                }

                // adds up the amount of people who have discounts for the month
                if (current.getDiscountMonths().contains(this.numToMonth(month_num))) {
                    discount_num += 1;
                }
            }
            // revenue = the expected revenue - (# people who didn't pay that month * 4 weeks a month * fee) 
            //         + (people who didn't pay last month paying back this month) - (# people with discounts * discount amount)
            int expected = total_attendance * member_fee;
            int curr_month_unpaid = curr_month_unpaid_count * 4 * member_fee;
            int prev_month_paid_back = prev_month_unpaid_count * 4 * member_fee;
            int discounts = discount_num * 5;
            // System.out.println(prev_month_unpaid_count + " | " + curr_month_unpaid_count);
            return expected - curr_month_unpaid + prev_month_paid_back - discounts;
        }
        return 0;
    }

    public int getExpenses(int month_num, int variable_cost) {
        if (1 <= month_num && month_num <= 12) {
            return hall_rent + variable_cost + coach.getCoachPayments(month_num) + coach.getUnpaidSalary();
        }
        return 0;
    }

    public int getProfit(int month_num, int variable_cost) {
        int profit = this.getRevenue(month_num) - this.getExpenses(month_num, variable_cost);
        return profit;
    }
}

class Coach {
    public String first_name;
    public String last_name;
    public String phone_number;
    public String address;
    public int coach_salary;
    public int unpaid_salary;
    public int[] monthly_inattendance = new int[12];
    public boolean paid_status;

    public Coach(String first, String last, String phone, String address, int coach_salary, int unpaid_salary, boolean paid_status) {
        this.first_name = first;
        this.last_name = last;
        this.phone_number = phone;
        this.address = address;
        this.coach_salary = coach_salary;
        this.unpaid_salary = unpaid_salary;
        this.monthly_inattendance = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.paid_status = paid_status;
    }
    public Coach() {
        this.first_name = "N/A";
        this.last_name = "N/A";
        this.phone_number = "123-456-7890";
        this.address = "Toronto, ON";
        this.coach_salary = 20;
        this.unpaid_salary = 0;
        this.monthly_inattendance = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.paid_status = true;
    }

    public String toString() {
        return "Coach: " + first_name + " " + last_name + " | " + phone_number + " | " + address + " | Salary/week: " + coach_salary + " | Unpaid Salary: " + unpaid_salary; 
    }

    public int[] getMonthlyAttendance() {
        return this.monthly_inattendance;
    }
    public void setMonthlyInattendance(int month_num, int new_monthly_inattendance) {
        if (1 <= month_num && month_num <= 12) {
            this.monthly_inattendance[month_num-1] = new_monthly_inattendance;
        }
    }

    public int getCoachPayments(int month_num) {
        if (!paid_status || month_num < 1 || month_num > 12) {
            // System.out.println("Error: enter the month (1 = Jan, ..., 12 = Dec)");
            return 0;
        }
        int salary = this.coach_salary*(4-monthly_inattendance[month_num-1]);
        return salary;
    }

    public void setUnpaidSalary(int unpaid) {
        this.unpaid_salary = unpaid;
    }
    public int getUnpaidSalary() {
        return this.unpaid_salary;
    }

    public boolean getPaidStatus() {
        return this.paid_status;
    }
    public void setPaidStatus(boolean new_paid_status) {
        this.paid_status = new_paid_status;
    }
}

class Member implements Comparable<Member> {
    public int id;
    public String first_name;
    public String last_name;
    public String phone_number;
    public String address;
    public int[] monthly_attendance = new int[12];
    public int times_attended;
    public int times_paid;
    public int times_unpaid;
    public boolean discount;
    public ArrayList<String> discount_months;
    public int amount_paid;
    public ArrayList<String> months_unpaid;
    

    public Member(int id, String first, String last, String phone, String address, int[] monthly, int attended, 
                    int times_paid, int times_unpaid, boolean discount, ArrayList<String> discount_months, 
                    int amount_paid, ArrayList<String> months_unpaid) {
        this.id = id;
        this.first_name = first;
        this.last_name = last;
        this.phone_number = phone;
        this.address = address;
        this.monthly_attendance = monthly;
        this.times_attended = attended;
        this.times_paid = times_paid;
        this.times_unpaid = times_unpaid;
        this.discount = discount;
        this.discount_months = discount_months;
        this.amount_paid = amount_paid;
        this.months_unpaid = months_unpaid;
    }
    public Member() {
        this.id = 0;
        this.first_name = "N/A";
        this.last_name = "N/A";
        this.phone_number = "123-456-7890";
        this.address = "Toronto, ON";
        this.monthly_attendance = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.times_attended = 0;
        this.times_paid = 0;
        this.times_unpaid = 0;
        this.discount = false;
        this.discount_months = new ArrayList<String>();
        this.amount_paid = 0;
        this.months_unpaid = new ArrayList<String>();
    }

    public String toString() {
        int i = 0;
        
        // the following creates a String for the months that the member has a discount for
        String months_discount_str = "(";
        for (i = 0; i < discount_months.size(); i++) {
            months_discount_str = months_discount_str.concat(discount_months.get(i) + ", ");
        }
        int len = months_discount_str.length();
        if (len > 1) {
            months_discount_str = months_discount_str.substring(0, len-2);  // the len-2 is to remove the trailing ", "
        }
        months_discount_str = months_discount_str.concat(")");

        // the following creates a String for the months that the member hasn't paid for
        String months_not_paid_str = "(";
        for (i = 0; i < months_unpaid.size(); i++) {
            months_not_paid_str = months_not_paid_str.concat(months_unpaid.get(i) + ", ");
        }
        len = months_not_paid_str.length();
        if (len > 1) {
            months_not_paid_str = months_not_paid_str.substring(0, len-2);
        }
        months_not_paid_str = months_not_paid_str.concat(")");

        return "ID: " + id + " | " + first_name + " " + last_name + " | " + phone_number + " | " + address + " | Times Attended: " 
        + times_attended + " | Times Paid: " + times_paid + " | Times Unpaid: " + times_unpaid  + " | Discount?: " + discount 
        + " | Months with Discount: " + months_discount_str + " | Amount Paid: " + amount_paid + " | Months Unpaid: " + months_not_paid_str;
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
    public void setMonthlyAttendance(int month_num, int new_monthly_attendance) {
        if (1 <= month_num && month_num <= 12) {
            this.monthly_attendance[month_num-1] = new_monthly_attendance;
        }
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

    public int getTimesUnPaid() {
        return this.times_paid;
    }
    public void setTimesUnPaid(int new_unpaid) {
        this.times_paid = new_unpaid;
    }

    public boolean getDiscountStatus() {
        return this.discount;
    }
    public void setDiscountStatus(boolean new_discount_status) {
        this.discount = new_discount_status;
    }

    public ArrayList<String> getDiscountMonths() {
        return this.discount_months;
    }
    public void setDiscountMonths(ArrayList<String> new_discount_months) {
        this.discount_months = new_discount_months;
    }
    public void addDiscountMonth(String month) {
        this.discount_months.add(month);
    }

    public int getAmountPaid() {
        return this.amount_paid;
    }
    public void getAmountPaid(int new_amount_paid) {
        this.times_paid = new_amount_paid;
    }

    public ArrayList<String> getMonthsUnpaid() {
        return this.months_unpaid;
    }
    public void setMonthsUnpaid(ArrayList<String> new_months_unpaid) {
        this.months_unpaid = new_months_unpaid;
    }

    @Override
    public int compareTo(Member member2) {
        return this.times_paid - member2.times_paid;
    }

}