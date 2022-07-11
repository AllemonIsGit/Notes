import com.example.domain.model.Note;
import com.example.repository.NoteRepository;
import com.example.repository.NoteRepositoryImpl;
import com.example.services.NoteService;
import com.example.services.NoteServiceImpl;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteRepositoryImplTests {

    @Test
    public void shouldReturnEmptyOptionalGivenNonExistentId() {
        // given
        NoteRepository noteRepository = new NoteRepositoryImpl("notes-test-db");

        // when
        Optional<Note> noteOptional = noteRepository.getById(234234234);

        // then
        assertThat(noteOptional).isEmpty();
    }

    @Test
    public void shouldReturnNonEmptyOptionalGivenExistentId() {
        // given
        NoteRepository noteRepository = new NoteRepositoryImpl();

        // when
        Optional<Note> noteOptional = noteRepository.getById(1);

        // then
        assertThat(noteOptional).isPresent();
    }

    @Test
    public void destroyNonExistentNote() {
        NoteService noteService = new NoteServiceImpl();

        noteService.destroy(134134134);
    }
}
