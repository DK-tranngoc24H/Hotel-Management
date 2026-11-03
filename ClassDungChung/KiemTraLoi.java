import java.util.Scanner;

/**
 * Lớp tiện ích kiểm tra dữ liệu đầu vào (toàn bộ là static, gọi trực tiếp: KiemTraLoi.xxx).
 * Phối hợp với ThongBao để in lỗi.
 */
public class KiemTraLoi {

    // 1. Chuỗi không được rỗng
    public static String nhapChuoiKhongRong(Scanner sc, String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            ThongBao.inLoi(ThongBao.LOI_DE_TRONG);
        }
    }

    // 2. Số nguyên >= 0
    public static int nhapSoNguyenDuong(Scanner sc, String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String input = sc.nextLine().trim();
            try {
                int so = Integer.parseInt(input);
                if (so >= 0) {
                    return so;
                }
                ThongBao.inLoi(ThongBao.LOI_SO_AM);
            } catch (NumberFormatException e) {
                ThongBao.inLoi(ThongBao.LOI_SO_KHONG_HOP_LE);
            }
        }
    }

    // 3. Số thực >= 0
    public static double nhapSoThucDuong(Scanner sc, String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String input = sc.nextLine().trim();
            try {
                double so = Double.parseDouble(input);
                if (so >= 0) {
                    return so;
                }
                ThongBao.inLoi(ThongBao.LOI_SO_AM);
            } catch (NumberFormatException e) {
                ThongBao.inLoi(ThongBao.LOI_SO_KHONG_HOP_LE);
            }
        }
    }

    // 4. Số nguyên trong khoảng [min, max] (dùng cho menu)
    public static int nhapSoNguyenTrongKhoang(Scanner sc, String thongBao, int min, int max) {
        while (true) {
            System.out.print(thongBao);
            String input = sc.nextLine().trim();
            try {
                int so = Integer.parseInt(input);
                if (so >= min && so <= max) {
                    return so;
                }
                ThongBao.inLoi("Vui long chon so tu " + min + " den " + max + "!");
            } catch (NumberFormatException e) {
                ThongBao.inLoi(ThongBao.LOI_SO_KHONG_HOP_LE);
            }
        }
    }

    // 5. Có / Không
    public static boolean nhapCoKhong(Scanner sc, String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            if (s.equalsIgnoreCase("co") || s.equalsIgnoreCase("có")
                    || s.equalsIgnoreCase("true") || s.equals("1")) {
                return true;
            }
            if (s.equalsIgnoreCase("khong") || s.equalsIgnoreCase("không")
                    || s.equalsIgnoreCase("false") || s.equals("0")) {
                return false;
            }
            ThongBao.inLoi(ThongBao.LOI_CHON_CO_KHONG);
        }
    }

    // 6. Trạng thái phòng
    public static String nhapTrangThaiPhong(Scanner sc) {
        while (true) {
            System.out.print("Nhap trang thai phong (Trong / Dang o / Da dat): ");
            String tt = sc.nextLine().trim();
            if (tt.equalsIgnoreCase("trong") || tt.equalsIgnoreCase("trống")) {
                return "Trong";
            }
            if (tt.equalsIgnoreCase("dang o") || tt.equalsIgnoreCase("đang ở") || tt.equalsIgnoreCase("dango")) {
                return "Dang o";
            }
            if (tt.equalsIgnoreCase("da dat") || tt.equalsIgnoreCase("đã đặt") || tt.equalsIgnoreCase("dadat")) {
                return "Da dat";
            }
            ThongBao.inLoi(ThongBao.LOI_TRANG_THAI);
        }
    }

    // 7. Loại view phòng VIP
    public static String nhapLoaiView(Scanner sc) {
        while (true) {
            System.out.print("Nhap loai view (Bien / Thanh pho / San vuon): ");
            String v = sc.nextLine().trim();
            if (v.equalsIgnoreCase("bien") || v.equalsIgnoreCase("biển")) {
                return "Bien";
            }
            if (v.equalsIgnoreCase("thanh pho") || v.equalsIgnoreCase("thành phố") || v.equalsIgnoreCase("thanhpho")) {
                return "Thanh pho";
            }
            if (v.equalsIgnoreCase("san vuon") || v.equalsIgnoreCase("sân vườn") || v.equalsIgnoreCase("sanvuon")) {
                return "San vuon";
            }
            ThongBao.inLoi(ThongBao.LOI_VIEW);
        }
    }
}
