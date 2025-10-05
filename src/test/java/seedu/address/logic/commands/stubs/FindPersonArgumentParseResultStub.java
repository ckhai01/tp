package seedu.address.logic.commands.stubs;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.parser.commandoption.Option;

/**
 * Mock stub for ArgumentParseResult to test FindCommand. Only stores the
 * list of keywords to find
 */
public class FindPersonArgumentParseResultStub extends ArgumentParseResultStub {
    private List<String> keywords;

    /**
     * @param keywords The string of keywords to find, internally parses into a List
     */
    public FindPersonArgumentParseResultStub(String keywords) {
        super();
        this.keywords = Arrays.asList(keywords.split("\\s+"));
    }

    // This stub should only be called with a flag to get the list of keywords
    @Override
    public <T> List<T> getAllValues(Option<T> flag) {
        @SuppressWarnings("unchecked")
        List<T> ret = (List<T>) keywords;
        return ret;
    }
}
