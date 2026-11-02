import java.util.Scanner;

public class MENUTV1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DANHSACHNGUOI ql = new DANHSACHNGUOI();
        String fileMacDinh = "danhsach_tv1.txt";

        while (true) {
            System.out.println("\n=======================================================");
            System.out.println("     CHUONG TRINH QUAN LY NHAN SU - THANH VIEN 1       ");
            System.out.println("=======================================================");
            System.out.println("1.  Nhap n doi tuong ban dau (Buoc 2.1.a)");
            System.out.println("2.  Xuat toan bo danh sach (Buoc 2.1.b - Da hinh)");
            System.out.println("3.  Them 1 doi tuong moi (Buoc 2.1.c)");
            System.out.println("4.  Them k doi tuong moi (Buoc 2.1.c)");
            System.out.println("5.  Sua thong tin theo ma / CCCD (Buoc 2.1.d)");
            System.out.println("6.  Xoa doi tuong theo ma / CCCD (Buoc 2.1.e)");
            System.out.println("7.  Tim kiem theo ma hoac ho ten (Buoc 2.1.f)");
            System.out.println("8.  Thong ke so lieu tong hop (Buoc 2.1.g)");
            System.out.println("9.  Nghiep vu: Tim nhan vien luong cao nhat (Buoc 2.1.h)");
            System.out.println("10. Ghi danh sach ra file (Buoc 4)");
            System.out.println("11. Doc danh sach tu file (Buoc 4)");
            System.out.println("12. Kiem tra tinh hop le SDT (Static - Buoc 5)");
            System.out.println("0.  Thoat chuong trinh");
            System.out.println("=======================================================");
            System.out.print("Nhap lua chon cua ban [0-12]: ");
            if (!sc.hasNextLine()) {
                break;
            }
            int luaChon = -1;
            try {
                luaChon = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                luaChon = -1;
            }

            switch (luaChon) {
                case 1:
                    ql.nhapDanhSach();
                    break;
                case 2:
                    ql.xuatDanhSach();
                    break;
                case 3:
                    ql.them1PhanTu();
                    break;
                case 4:
                    ql.themKPhanTu();
                    break;
                case 5:
                    ql.suaTheoMa();
                    break;
                case 6:
                    ql.xoaTheoMa();
                    break;
                case 7:
                    ql.timKiem();
                    break;
                case 8:
                    ql.thongKe();
                    break;
                case 9:
                    ql.timNhanVienLuongCaoNhat();
                    break;
                case 10:
                    System.out.print("Nhap ten file can luu (Enter de dung mac dinh '" + fileMacDinh + "'): ");
                    String fSave = sc.nextLine().trim();
                    if (fSave.isEmpty()) fSave = fileMacDinh;
                    ql.ghiFile(fSave);
                    break;
                case 11:
                    System.out.print("Nhap ten file can doc (Enter de dung mac dinh '" + fileMacDinh + "'): ");
                    String fLoad = sc.nextLine().trim();
                    if (fLoad.isEmpty()) fLoad = fileMacDinh;
                    ql.docFile(fLoad);
                    break;
                case 12:
                    System.out.print("Nhap SDT can kiem tra: ");
                    String sdtTest = sc.nextLine();
                    if (DANHSACHNGUOI.kiemTraSDT(sdtTest)) {
                        System.out.println(">> SDT hop le (10 so bat dau bang 0)");
                    } else {
                        System.out.println(">> SDT khong hop le!");
                    }
                    break;
                case 0:
                    System.out.println(">> Cam on ban da su dung chuong trinh!");
                    return;
                default:
                    System.out.println(">> Lua chon khong hop le, vui long chon lai tu 0 den 12!");
                    break;
            }
        }
    }
}
