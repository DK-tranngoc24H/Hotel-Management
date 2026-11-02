import java.util.Scanner;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        ArrayList<Phong> danhsachphong = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("chon loai phong muon nhap (1-stand|2-phongvip)");
        int chon = sc.nextInt();
        sc.nextLine();
        if (chon == 1) {
            PhongStandard ps = new PhongStandard();
            System.out.println("nhap phongstandard");
            ps.nhap(sc);
            danhsachphong.add(ps);
        } else if (chon == 2) {
            PhongVIP pv = new PhongVIP();
            System.out.println("nhap phong vip");
            pv.nhap(sc);
            danhsachphong.add(pv);
        } else {
            System.out.println("khong hop le");
        }
        for (Phong p : danhsachphong) {
            p.xuat();
        }
        sc.close();
    }
}
