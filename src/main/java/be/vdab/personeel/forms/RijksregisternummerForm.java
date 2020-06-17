package be.vdab.personeel.forms;

import be.vdab.personeel.constraints.RijksregisterNr;

import javax.validation.constraints.NotNull;

public class RijksregisternummerForm {

    @NotNull @RijksregisterNr
    private final Long rijksregisternummer;

    public RijksregisternummerForm(Long rijksregisternummer) {
        this.rijksregisternummer = rijksregisternummer;
    }

    public long getRijksregisternummer() {
        return rijksregisternummer;
    }
}
