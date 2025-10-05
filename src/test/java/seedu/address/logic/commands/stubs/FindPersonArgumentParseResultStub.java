package seedu.address.logic.commands.stubs;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.parser.Flag;

public class FindPersonArgumentParseResultStub extends ArgumentParseResultStub {
    private List<String> keywords;

    public FindPersonArgumentParseResultStub(String keywords) {
        super();
        this.keywords = Arrays.asList(keywords.split("\\s+"));
    }

    // This stub should only be called with a flag to get the list of keywords
    @Override
    public <T> List<T> getAllValues(Flag<T> flag) {
        @SuppressWarnings("unchecked")
        List<T> ret = (List<T>) keywords;
        return ret;
    }
}
