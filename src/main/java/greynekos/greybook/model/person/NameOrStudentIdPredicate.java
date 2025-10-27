// greynekos.greybook.model.person.NameOrStudentIdPredicate.java
package greynekos.greybook.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import greynekos.greybook.commons.util.StringUtil;

public class NameOrStudentIdPredicate implements Predicate<Person> {

    private final List<String> keywords;
    private final List<String> idFragmentsUp;

    public NameOrStudentIdPredicate(List<String> keywords, List<String> idFragments) {
        requireNonNull(keywords);
        requireNonNull(idFragments);
        this.keywords = keywords;
        this.idFragmentsUp = idFragments.stream().map(s -> s.toUpperCase(Locale.ROOT)).toList();
    }

    @Override
    public boolean test(Person person) {
        String fullName = person.getName().fullName;

        boolean matchesName = !keywords.isEmpty() && keywords.stream().filter(kw -> !kw.isBlank())
                .anyMatch(kw -> StringUtil.containsWordIgnoreCase(fullName, kw));

        String idUp = person.getStudentID() == null ? "" : person.getStudentID().toString().toUpperCase(Locale.ROOT);

        boolean matchesAnyIdFrag =
                !idFragmentsUp.isEmpty() && idFragmentsUp.stream().filter(f -> !f.isBlank()).anyMatch(idUp::contains);

        return matchesName || matchesAnyIdFrag;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof NameOrStudentIdPredicate))
            return false;
        NameOrStudentIdPredicate o = (NameOrStudentIdPredicate) other;
        return keywords.equals(o.keywords) && idFragmentsUp.equals(o.idFragmentsUp);
    }

    @Override
    public int hashCode() {
        return keywords.hashCode() * 31 + idFragmentsUp.hashCode();
    }
}
