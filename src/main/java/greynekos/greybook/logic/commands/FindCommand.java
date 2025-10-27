package greynekos.greybook.logic.commands;

import static greynekos.greybook.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import greynekos.greybook.logic.Messages;
import greynekos.greybook.logic.commands.exceptions.CommandException;
import greynekos.greybook.logic.parser.ArgumentParseResult;
import greynekos.greybook.logic.parser.GreyBookParser;
import greynekos.greybook.logic.parser.commandoption.OptionalPrefixOption;
import greynekos.greybook.logic.parser.commandoption.OptionalSinglePreambleOption;
import greynekos.greybook.model.Model;
import greynekos.greybook.model.person.NameOrStudentIdPredicate;
import greynekos.greybook.model.person.Person;

/**
 * Finds persons by name keywords (case-insensitive) and/or student ID fragment (i/…).
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds persons by name keywords and/or student ID.\n"
            + "Parameters: [KEYWORD [MORE_KEYWORDS]...] [" + PREFIX_STUDENTID + "ID_OR_FRAGMENT]\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " alice bob\n"
            + "  " + COMMAND_WORD + " " + PREFIX_STUDENTID + "A0123456X\n"
            + "  " + COMMAND_WORD + " " + PREFIX_STUDENTID + "12345   (partial match)\n"
            + "  " + COMMAND_WORD + " john " + PREFIX_STUDENTID + "A0123456X";

    // Optional preamble: user may provide zero or more words; we accept the whole string and split.
    private final OptionalSinglePreambleOption<String> preambleOption =
            OptionalSinglePreambleOption.of("KEYWORDS");

    // Optional ID fragment: treat as free-form string, not a strict StudentId.
    private final OptionalPrefixOption<String> studentIdFragmentOption =
            OptionalPrefixOption.of(PREFIX_STUDENTID, "ID_OR_FRAGMENT", s -> {
                String t = s == null ? "" : s.trim();
                if (t.isEmpty()) {
                    throw new IllegalArgumentException("Student ID fragment cannot be empty");
                }
                return t;
            });

    @Override
    public void addToParser(GreyBookParser parser) {
        parser.newCommand(COMMAND_WORD, MESSAGE_USAGE, this)
                .addOptions(preambleOption, studentIdFragmentOption);
    }

    @Override
    public CommandResult execute(Model model, ArgumentParseResult arg) throws CommandException {
        requireNonNull(model);

        Predicate<Person> predicate = getParseResult(arg);
        model.updateFilteredPersonList(predicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public Predicate<Person> getParseResult(ArgumentParseResult argResult) {
        // Split optional preamble into keywords, filtering blanks
        List<String> keywords = argResult.getOptionalValue(preambleOption)
                .map(s -> Arrays.stream(s.trim().split("\\s+"))
                        .filter(token -> !token.isBlank())
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        Optional<String> idFragment = argResult.getOptionalValue(studentIdFragmentOption)
                .map(String::trim)
                .filter(s -> !s.isEmpty());

        return new NameOrStudentIdPredicate(keywords, idFragment);
    }
}
