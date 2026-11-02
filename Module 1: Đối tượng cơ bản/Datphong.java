import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Datphong {
    Scanner sc = new Scanner(System.in);
    private String maDatPhong;
    private String maPhong;
    private Date ngayDat;
    private Date ngayTra;
    private double giaTien;

    public Datphong() {
        this.maDatPhong = "";
        this.maPhong = "";
        this.ngayDat = new Date();
        this.ngayTra = new Date();
        this.giaTien = 0.0;
    }

    public Datphong(String maDatPhong, String maPhong, Date ngayDat, Date ngayTra, double giaTien) {
        this.maDatPhong = maDatPhong;
        this.maPhong = maPhong;
        this.ngayDat = ngayDat;
        this.ngayTra = ngayTra;
        this.giaTien = giaTien;
    }

    public void nhap() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        System.out.print("Nhap ma dat phong: ");
        this.maDatPhong = sc.nextLine();
        System.out.print("Nhap ma phong: ");
        this.maPhong = sc.nextLine();

        this.ngayDat = nhapNgay(sdf, "Nhap ngay dat (dd/MM/yyyy): ");
        this.ngayTra = nhapNgay(sdf, "Nhap ngay tra (dd/MM/yyyy): ");

        System.out.print("Nhap gia tien: ");
        this.giaTien = Double.parseDouble(sc.nextLine());
    }

    private Date nhapNgay(SimpleDateFormat sdf, String loiNhac) {
        while (true) {
            try {
                System.out.print(loiNhac);
                return sdf.parse(sc.nextLine());
            } catch (ParseException e) {
                System.out.println("Ngay khong hop le, nhap lai theo dang dd/MM/yyyy.");
            }
        }
    }

    public void xuat() {
        System.out.println(toString());
    }

    public String getMaDatPhong() {
        return maDatPhong;
    }

    public void setMaDatPhong(String maDatPhong) {
        this.maDatPhong = maDatPhong;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public Date getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(Date ngayDat) {
        this.ngayDat = ngayDat;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Datphong{" +
                "maDatPhong='" + maDatPhong + '\'' +
                ", maPhong='" + maPhong + '\'' +
                ", ngayDat=" + sdf.format(ngayDat) +
                ", ngayTra=" + sdf.format(ngayTra) +
                ", giaTien=" + giaTien +
                '}';
    }
}
