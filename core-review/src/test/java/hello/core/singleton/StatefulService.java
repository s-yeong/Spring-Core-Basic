package hello.core.singleton;

public class StatefulService {

    // 상태를 유지하는 필드
//    private int price;

    // before void -> after int
    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
//        this.price = price; // <--- 문제!
        return price;
    }


//    public int getPrice() {
//        return price;
//    }

}
