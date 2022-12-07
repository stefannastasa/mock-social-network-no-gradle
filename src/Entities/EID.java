package Entities;

import Utils.SimpleEncoder;

import java.util.Arrays;

/**
 * Class for managing Entity ID's (hence the name EID). ID's can be generated from any form of text.
 * */
public class EID {

    SimpleEncoder enc = SimpleEncoder.getInstance();

    public byte[] getRaw() {
        return raw;
    }

    byte[] raw;

    /**
     * Constructor takes a string and memorises its hash value.
     *
     * @param doBy string whose encoded value results in the EID
     * */
    public EID(String doBy){
        raw = enc.Encode(doBy);
    }
    public EID(byte[] doBy){this.raw = doBy;}

    /**
     * For two EID classes to be equal, they need to have the exact same raw value
     * @param o the other EID to compare with
     *
     * @returns True if the calling EID and o have the same raw value.
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EID EID = (EID) o;
        return Arrays.equals(raw, EID.raw);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(raw);
    }
}
