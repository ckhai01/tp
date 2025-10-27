package greynekos.greybook.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

import greynekos.greybook.commons.util.StringUtil;

/**
 * Matches a person if:
 *  - their name contains ANY of the provided keywords (case-insensitive), OR
 *  - their student ID string contains the provided fragment (case-insensitive), if present.
 */
public class NameOrStudentIdPredicate implements Predicate<Person> {

    private final List<String> keywords;              // may be empty
    private final Optional<String> studentIdFragment; // normalized to upper-case, may be empty

    public NameOrStudentIdPredicate(List<String> keywords, Optional<String> studentIdFragment) {
        requireNonNull(keywords);
        requireNonNull(studentIdFragment);
        this.keywords = keywords;
        this.studentIdFragment = studentIdFragment.map(s -> s.toUpperCase(Locale.ROOT));
    }

    @Override
    public boolean test(Person person) {
        boolean matchesName = false;

        if (!keywords.isEmpty()) {
            String fullName = person.getName().fullName; // keep original for StringUtil
            for (String kw : keywords) {
                if (!kw.isBlank() && StringUtil.containsWordIgnoreCase(fullName, kw)) {
                    matchesName = true;
                    break;
                }
            }
        }

        boolean matchesStudentId = studentIdFragment
                .map(frag -> {
                    String id = person.getStudentID().toString(); // or .value if you expose it
                    return id != null && id.toUpperCase(Locale.ROOT).contains(frag);
                })
                .orElse(false);

        return matchesName || matchesStudentId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof NameOrStudentIdPredicate)) return false;
        NameOrStudentIdPredicate o = (NameOrStudentIdPredicate) other;
        return keywords.equals(o.keywords) && studentIdFragment.equals(o.studentIdFragment);
    }

    @Override
    public String toString() {
        return String.format("NameOrStudentIdPredicate{keywords=%s, studentIdFragment=%s}",
                keywords, studentIdFragment);
    }
}
