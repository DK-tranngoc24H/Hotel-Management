import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class DanhSachPhong {
    private final ArrayList<Phong> ds = new ArrayList<>();
    private final Scanner sc;

    public DanhSachPhong() {
        this.sc = new Scanner(System.in);
        // Nạp sẵn vài phòng để chạy demo ngay
        ds.add(new PhongStandard("P101", 500000.0, "Trong", false));
        ds.add(new PhongStandard("P102", 500000.0, "Trong", true));
        ds.add(new PhongVIP("P201", 1200000.0, "Trong", "Bien", 200000.0));
    }

    public void themPhong() {
        System.out.println("chon loai phong can them(1/phtandard\n2/phongVip\n)\n");
        int loai = 0;
        loai = sc.nextInt();
        sc.nextLine();
        Phong pmoi;
        if (loai == 1) {
            pmoi = new PhongStandard();
        } else {
            pmoi = new PhongVIP();
        }
        pmoi.nhap(sc);
        if (timKiemTheoMaPhong(pmoi.getMaPhong()) != null) {
            System.out.println("da ton tai");
            return;
        }
        ds.add(pmoi);
        System.out.println("them phong moi thanh cong");
    }

    public void xoaPhong() {
        System.out.println("chon loai phong ma xoa ");
        String ma = sc.nextLine();
        Phong p = timKiemTheoMaPhong(ma);
        if (p == null) {
            System.out.println("khong tim thay phong:");
            return;
        }
        if (!p.conTrong()) {
            System.out.println("phong dang co nguoi thue:");
            return;
        }
        ds.remove(p);
        System.out.println("da xoa phong");
    }

    public void suaPhong() {
        System.out.print("Nhap ma phong can sua: ");
        String ma = sc.nextLine();
        Phong p = timKiemTheoMaPhong(ma);
        if (p == null) {
            System.out.println("Khong tim thay phong voi ma: " + ma);
            return;
        }
        System.out.println("Tim thay phong! Nhap lai thong tin moi:");
        p.nhap(sc);
        System.out.println(">> Cap nhat thong tin phong thanh cong!");
    }

    public ArrayList<Phong> getDanhSach() {
        return ds;
    }

    public Phong timKiemTheoMaPhong(String ma) {
        if (ma == null)
            return null;
        for (Phong p : ds) {
            if (p.getMaPhong().equalsIgnoreCase(ma.trim()))
                return p;
        }
        return null;
    }

    public int locPhongTrong() {
        int dem = 0;
        System.out.println("danh sach phong con trong");
        for (Phong p : ds) {
            if (p.conTrong()) {
                p.xuat();
                System.out.println();
                dem++;
            }
        }
        if (dem == 0) {
            System.out.println("Hien tai da het phong trong!");
        }
        return dem;
    }

    public void xuatDanhSach() {
        if (ds.isEmpty()) {
            System.out.println("danh sach phong trong");
            return;
        }
        for (Phong p : ds) {
            p.xuat();
        }
    }
}


