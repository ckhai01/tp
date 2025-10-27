package greynekos.greybook.logic.commands;

import static greynekos.greybook.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import greynekos.greybook.logic.Messages;
import greynekos.greybook.logic.commands.exceptions.CommandException;
import greynekos.greybook.logic.parser.ArgumentParseResult;
import greynekos.greybook.logic.parser.GreyBookParser;
import greynekos.greybook.logic.parser.ParserUtil;
import greynekos.greybook.logic.parser.commandoption.OptionalSinglePreambleOption;
import greynekos.greybook.logic.parser.commandoption.ZeroOrMorePrefixOption;
import greynekos.greybook.model.Model;
import greynekos.greybook.model.person.NameOrStudentIdPredicate;
import greynekos.greybook.model.person.Person;

/**
 * Finds and lists all persons in GreyBook whose name contains any of the
 * argument keywords, or student ID contains any of the provided ID fragments.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds persons by name keywords and/or student ID.\n"
            + "Parameters: [KEYWORD [MORE_KEYWORDS]...] [" + PREFIX_STUDENTID + "ID_FRAGMENT]...\n" + "Examples:\n"
            + "  " + COMMAND_WORD + " alice bob\n" + "  " + COMMAND_WORD + " i/12345 i/A0123456X\n" + "  "
            + COMMAND_WORD + " i/12345 alex\n" + "  " + COMMAND_WORD + " alex i/12345";

    public static final String MESSAGE_EMPTY_COMMAND = "Please provide at least one name keyword or an i/ID fragment.";

    private final OptionalSinglePreambleOption<String> preambleOption = OptionalSinglePreambleOption.of("KEYWORDS");

    private final ZeroOrMorePrefixOption<String> studentIdFragmentsOption =
            ZeroOrMorePrefixOption.of(PREFIX_STUDENTID, "ID_FRAGMENT", s -> s == null ? "" : s.trim());

    @Override
    public void addToParser(GreyBookParser parser) {
        parser.newCommand(COMMAND_WORD, MESSAGE_USAGE, this).addOptions(studentIdFragmentsOption, preambleOption);
    }

    @Override
    public CommandResult execute(Model model, ArgumentParseResult arg) throws CommandException {
        requireNonNull(model);

        ParserUtil.KeywordsAndIdFrags parsed =
                ParserUtil.parseKeywordsAndIdFrags(arg, preambleOption, studentIdFragmentsOption);

        if (parsed.keywords().isEmpty() && parsed.idFrags().isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_COMMAND);
        }

        Predicate<Person> predicate = getParseResult(arg);
        model.updateFilteredPersonList(predicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public Predicate<Person> getParseResult(ArgumentParseResult argResult) {
        ParserUtil.KeywordsAndIdFrags parsed =
                ParserUtil.parseKeywordsAndIdFrags(argResult, preambleOption, studentIdFragmentsOption);
        return new NameOrStudentIdPredicate(parsed.keywords(), parsed.idFrags());
    }

}
