package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;

public class FilePattern {
    private String name;
    private byte[] signature;

    public FilePattern(String name, byte[] signature) {
        this.name = name;
        this.signature = signature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public boolean match(File file) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        byte[] fileSignature = new byte[signature.length];
        try {
            int num_bytes = fis.read(fileSignature);
            if (num_bytes != signature.length) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < signature.length; i++) {
            if (signature[i] != fileSignature[i]) {
                return false;
            }
        }
        return true;
    }

    static public String byteToHex(byte b) {
        return String.format("%02X", b);
    }

    static public String byteArrayToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(byteToHex(b));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilePattern that = (FilePattern) o;
        return getName().equals(that.getName()) && Arrays.equals(getSignature(), that.getSignature());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getName());
        result = 31 * result + Arrays.hashCode(getSignature());
        return result;
    }

    @Override
    public String toString() {
        return "FilePattern{" +
                "name='" + name + '\'' +
                ", signature=" + byteArrayToHex(signature) +
                '}';
    }
}
