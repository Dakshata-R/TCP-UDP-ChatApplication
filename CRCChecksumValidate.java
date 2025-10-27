import java.util.*;

public class CRCChecksumValidate {

    // XOR two binary strings (used in CRC)
    static String xor(String a, String b) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < b.length(); i++) {
            result.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
        }
        return result.toString();
    }

    // Perform CRC division and return remainder
    static String crcDivision(String dividend, String divisor) {
        int n = divisor.length();
        String temp = dividend.substring(0, n);

        while (n < dividend.length()) {
            if (temp.charAt(0) == '1')
                temp = xor(divisor, temp) + dividend.charAt(n);
            else
                temp = xor("0".repeat(divisor.length()), temp) + dividend.charAt(n);
            n++;
        }

        if (temp.charAt(0) == '1')
            temp = xor(divisor, temp);
        else
            temp = xor("0".repeat(divisor.length()), temp);

        return temp;
    }

    // Compute codeword by appending CRC
    static String computeCRC(String data, String divisor) {
        String appended = data + "0".repeat(divisor.length() - 1);
        String remainder = crcDivision(appended, divisor);
        return data + remainder;
    }

    // Verify CRC at receiver
    static boolean verifyCRC(String codeword, String divisor) {
        String remainder = crcDivision(codeword, divisor);
        return remainder.chars().allMatch(ch -> ch == '0');
    }

    // Compute simple checksum (8-bit blocks)
    static String checksum(List<String> blocks, int bits) {
        int total = 0;
        for (String block : blocks) {
            total += Integer.parseInt(block, 2);
            // Wrap around overflow
            total = (total & ((1 << bits) - 1)) + (total >> bits);
        }
        int result = (~total) & ((1 << bits) - 1); // 1's complement
        return String.format("%" + bits + "s", Integer.toBinaryString(result)).replace(' ', '0');
    }

    // Verify checksum
    static boolean verifyChecksum(List<String> blocks, String checksum, int bits) {
        int total = 0;
        for (String block : blocks) {
            total += Integer.parseInt(block, 2);
            total = (total & ((1 << bits) - 1)) + (total >> bits);
        }
        total += Integer.parseInt(checksum, 2);
        total = (total & ((1 << bits) - 1)) + (total >> bits);
        return total == (1 << bits) - 1; // all ones → no error
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose Algorithm:");
        System.out.println("1. CRC");
        System.out.println("2. Checksum");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            // --- CRC Section ---
            System.out.print("Enter binary data: ");
            String data = sc.nextLine();

            System.out.print("Enter divisor: ");
            String divisor = sc.nextLine();

            String codeword = computeCRC(data, divisor);
            System.out.println("Codeword (Data + CRC): " + codeword);

            // --- Simulate error for testing ---
            System.out.print("Simulate error? (y/n): ");
            if (sc.nextLine().equalsIgnoreCase("y")) {
                char flipped = codeword.charAt(0) == '0' ? '1' : '0';
                codeword = flipped + codeword.substring(1);
                System.out.println("Codeword with error: " + codeword);
            }

            // Verification
            if (verifyCRC(codeword, divisor))
                System.out.println("Receiver: Data Accepted (No Error)");
            else
                System.out.println("Receiver: Error Detected!");

        } else if (choice == 2) {
            // --- Checksum Section ---
            System.out.print("Enter number of 8-bit blocks: ");
            int n = sc.nextInt();
            sc.nextLine();
            List<String> blocks = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                System.out.print("Enter block " + (i + 1) + ": ");
                blocks.add(sc.nextLine());
            }

            String csum = checksum(blocks, 8);
            System.out.println("Checksum: " + csum);

            // --- Simulate error in first block ---
            System.out.print("Simulate error in first block? (y/n): ");
            if (sc.nextLine().equalsIgnoreCase("y")) {
                String firstBlock = blocks.get(0);
                char flipped = firstBlock.charAt(0) == '0' ? '1' : '0';
                blocks.set(0, flipped + firstBlock.substring(1));
                System.out.println("Blocks with error: " + blocks);
            }

            // Verification
            if (verifyChecksum(blocks, csum, 8))
                System.out.println("Receiver: Data Accepted (No Error)");
            else
                System.out.println("Receiver: Error Detected!");
        }
    }
}
