package noob.impl;

import noob.school.School;

public class HighSchool implements School {
    @Override
    public int years() {
        return 3;
    }

    @Override
    public boolean isCompulsory() {
        return true;
    }
}
