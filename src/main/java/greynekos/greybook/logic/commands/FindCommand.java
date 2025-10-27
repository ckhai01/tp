package greynekos.greybook.logic.commands;

import static greynekos.greybook.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import greynekos.greybook.logic.Messages;
import greynekos.greybook.logic.commands.exceptions.CommandException;
import greynekos.greybook.logic.parser.ArgumentParseResult;
import greynekos.greybook.logic.parser.GreyBookParser;
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

        List<String> keywords = new ArrayList<>();
        List<String> idFrags = new ArrayList<>();
        parseKeywordsAndIdFrags(arg, keywords, idFrags);

        if (keywords.isEmpty() && idFrags.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_COMMAND);
        }

        Predicate<Person> predicate = new NameOrStudentIdPredicate(keywords, idFrags);
        model.updateFilteredPersonList(predicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public Predicate<Person> getParseResult(ArgumentParseResult argResult) {
        List<String> keywords = new ArrayList<>();
        List<String> idFrags = new ArrayList<>();
        parseKeywordsAndIdFrags(argResult, keywords, idFrags);
        return new NameOrStudentIdPredicate(keywords, idFrags);
    }

    private void parseKeywordsAndIdFrags(ArgumentParseResult arg, List<String> keywordsOut, List<String> idFragsOut) {
        arg.getOptionalValue(preambleOption).map(
                s -> Arrays.stream(s.trim().split("\\s+")).filter(tok -> !tok.isBlank()).collect(Collectors.toList()))
                .ifPresent(keywordsOut::addAll);

        for (String raw : arg.getAllValues(studentIdFragmentsOption)) {
            if (raw == null) {
                continue;
            }
            String trimmed = raw.trim();
            if (trimmed.isEmpty()) {
                continue;
            }

            String[] parts = trimmed.split("\\s+");
            idFragsOut.add(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                String tok = parts[i];
                if (!tok.isBlank()) {
                    keywordsOut.add(tok);
                }
            }
        }
    }
}
