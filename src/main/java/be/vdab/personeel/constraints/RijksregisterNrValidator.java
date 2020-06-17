package be.vdab.personeel.constraints;

import be.vdab.personeel.domain.Werknemer;
import be.vdab.personeel.repositories.WerknemerRepository;
import be.vdab.personeel.sessions.Identificatie;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Objects;

public class RijksregisterNrValidator implements ConstraintValidator<RijksregisterNr, Long> {


    private final WerknemerRepository repository;
    private final Identificatie identificatie;

    public RijksregisterNrValidator(WerknemerRepository repository, Identificatie identificatie) {
        this.repository = repository;
        this.identificatie=identificatie;
    }

    @Override
    public void initialize(RijksregisterNr constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long rijksregisterNr, ConstraintValidatorContext context) {
        // not a matter of existence, but validity; hence, if it doesn't exist, this is not problem of the validator
        if (rijksregisterNr == null) {
            return true;
        }
        //length of rijks is matter of validity: if rijks is lesser or bigger than 11 digits, drop it;
        if (String.valueOf(rijksregisterNr).length()!=11){
            return false;
        }
        // database retrieval and sorting of repositoryDateFragments
        String[] pieces = (repository.findById(identificatie.getId()).get().getGeboorte()).toString().split("-");

        // checking for equality of year, month and day, first 6 numbers in rijks...
        //.equals(repositoryDateFragment, StringifiedInputFragment)

        if (!Objects.equals(pieces[0].substring(2,4), String.valueOf(rijksregisterNr).substring(0,2))
                || !Objects.equals(pieces[1], String.valueOf(rijksregisterNr).substring(2,4))
                || !Objects.equals(pieces[2], String.valueOf(rijksregisterNr).substring(4,6))){
            return false;
        }

        // checkings for modulo and equality
        long last2Digits = rijksregisterNr % 100L;
        long remainDigits = rijksregisterNr / 100L;
        if (Long.parseLong(pieces[0]) >= 2000L){
            remainDigits = Long.parseLong("2"+remainDigits);
        }
        return last2Digits == 97 - (remainDigits % 97);

    }


}
