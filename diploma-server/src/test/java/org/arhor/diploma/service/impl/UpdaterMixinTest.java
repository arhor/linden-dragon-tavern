package org.arhor.diploma.service.impl;

import org.arhor.diploma.commons.Identifiable;
import org.arhor.diploma.data.persistence.domain.core.DomainObject;
import org.arhor.diploma.data.persistence.repository.BaseRepository;
import org.arhor.diploma.exception.EntityNotFoundException;
import org.arhor.diploma.commons.Converter;
import org.arhor.diploma.testutils.RandomParameter;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.annotation.Testable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Testable
@ExtendWith({MockitoExtension.class, RandomParameter.Resolver.class})
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class UpdaterMixinTest {

    @Mock private Converter<DomainObject<String>, Identifiable<String>> converter;
    @Mock private BaseRepository<DomainObject<String>, String> repository;
    @Mock private Identifiable<String> testDto;
    @Mock private DomainObject<String> testEntity;

    @InjectMocks
    private UpdaterMixin<DomainObject<String>, Identifiable<String>, String> updaterUnderTest;

    @Test
    void shouldThrowIllegalArgumentException() {
        // given
        doReturn(null).when(testDto).getId();

        // when
        ThrowingCallable action = () -> updaterUnderTest.update(testDto);

        // then
        assertThatThrownBy(action)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(UpdaterMixin.KEY_PROPERTY);

        verifyNoInteractions(repository, converter);
    }

    @Test
    void shouldThrowEntityNotFoundException(@RandomParameter final String testId) {
        // given
        final var entityName = "TEST_ENTITY";

        doReturn(testId).when(testDto).getId();
        doReturn(Optional.empty()).when(repository).findById(testId);
        doReturn(entityName).when(repository).getEntityName();

        // when
        ThrowingCallable action = () -> updaterUnderTest.update(testDto);

        // then
        assertThatThrownBy(action)
                .isInstanceOf(EntityNotFoundException.class)
                .hasFieldOrPropertyWithValue("entityName", entityName)
                .hasFieldOrPropertyWithValue("propertyName", UpdaterMixin.KEY_PROPERTY)
                .hasFieldOrPropertyWithValue("propertyValue", testId);

        verify(repository).findById(testId);
        verifyNoInteractions(converter);
    }

    @Test
    void shouldSavePassedObjectWithoutException(@RandomParameter final String testId) {
        // given
        doReturn(testId).when(testDto).getId();
        doReturn(Optional.of(testEntity)).when(repository).findById(testId);
        doReturn(testEntity).when(repository).save(testEntity);
        doReturn(testDto).when(converter).entityToDto(testEntity);

        // when
        final var result = updaterUnderTest.update(testDto);

        // then
        assertThat(result)
                .isNotNull()
                .isEqualTo(testDto);
    }
}
