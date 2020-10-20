package org.arhor.diploma.service.impl;

import org.arhor.diploma.commons.MutableIdentity;
import org.arhor.diploma.data.persist.domain.core.DomainObject;
import org.arhor.diploma.data.persist.repository.BaseRepository;
import org.arhor.diploma.exception.EntityNotFoundException;
import org.arhor.diploma.service.mapping.Converter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.annotation.Testable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.arhor.diploma.TestUtils.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Testable
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ReaderMixinTest {

    @Mock private Converter<DomainObject<String>, MutableIdentity<String>> converter;
    @Mock private BaseRepository<DomainObject<String>, String> repository;
    @Mock private MutableIdentity<String> testDto;
    @Mock private DomainObject<String> testEntity;

    @Test
    void shouldReturnProperResultList() {
        when(converter.entityToDto(testEntity)).thenReturn(testDto);
        when(repository.findAll()).thenReturn(List.of(testEntity));

        // given
        final var reader = new ReaderMixin<>(converter, repository);
        
        // when
        final var result = reader.getList();
        
        // then
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .contains(testDto)
                .hasSize(1);

        verify(converter).entityToDto(testEntity);
        verify(repository).findAll();
    }

    @Test
    void shouldReturnExactlyRequiredObject() {
        when(converter.entityToDto(testEntity)).thenReturn(testDto);
        when(repository.findById(anyString())).thenReturn(Optional.of(testEntity));

        // given
        final var reader = new ReaderMixin<>(converter, repository);
        final var search = randomString();

        // when
        final var result = reader.getOne(search);

        // then
        assertThat(result)
                .isNotNull()
                .isSameAs(testDto);

        verify(converter).entityToDto(testEntity);
        verify(repository).findById(search);
    }

    @Test
    void shouldThrowAnException() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        // given
        final var reader = new ReaderMixin<>(converter, repository);
        final var search = randomString();

        // then
        assertThatThrownBy(() -> reader.getOne(search))
                .isInstanceOf(EntityNotFoundException.class)
                .hasFieldOrPropertyWithValue("fieldValue", search);

        verify(repository).findById(search);
    }
}
