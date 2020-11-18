package org.arhor.diploma.service.impl;

import org.arhor.diploma.commons.MutableIdentity;
import org.arhor.diploma.data.persist.domain.core.DomainObject;
import org.arhor.diploma.data.persist.repository.BaseRepository;
import org.arhor.diploma.exception.EntityNotFoundException;
import org.arhor.diploma.commons.Converter;
import org.arhor.diploma.testutils.RandomParameter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.annotation.Testable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Testable
@ExtendWith({MockitoExtension.class, RandomParameter.Resolver.class})
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ReaderMixinTest {

    @Mock private Converter<DomainObject<String>, MutableIdentity<String>> converter;
    @Mock private BaseRepository<DomainObject<String>, String> repository;
    @Mock private MutableIdentity<String> testDto;
    @Mock private DomainObject<String> testEntity;
    @Mock private Page<DomainObject<String>> pageResponse;

    @InjectMocks
    private ReaderMixin<DomainObject<String>, MutableIdentity<String>, String> readerUnderTest;

    @Test
    void shouldReturnProperResultList() {
        // given
        doReturn(testDto).when(converter).entityToDto(testEntity);
        doReturn(List.of(testEntity)).when(repository).findAll();
        
        // when
        final var result = readerUnderTest.getList();
        
        // then
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .contains(testDto);

        verify(converter).entityToDto(testEntity);
        verify(repository).findAll();
    }

    @Test
    void shouldReturnExactlyRequiredObject(@RandomParameter final String search) {
        // given
        doReturn(testDto).when(converter).entityToDto(testEntity);
        doReturn(Optional.of(testEntity)).when(repository).findById(search);

        // when
        final var result = readerUnderTest.getOne(search);

        // then
        assertThat(result)
                .isNotNull()
                .isSameAs(testDto);

        verify(converter).entityToDto(testEntity);
        verify(repository).findById(search);
    }

    @Test
    void shouldThrowAnException(@RandomParameter final String search) {
        // given
        final var entityName = "TEST_ENTITY";

        doReturn(Optional.empty()).when(repository).findById(search);
        doReturn(entityName).when(repository).getEntityName();

        // then
        assertThatThrownBy(() -> readerUnderTest.getOne(search))
                .isInstanceOf(EntityNotFoundException.class)
                .hasFieldOrPropertyWithValue("entityName", entityName)
                .hasFieldOrPropertyWithValue("propertyName", ReaderMixin.KEY_PROPERTY)
                .hasFieldOrPropertyWithValue("propertyValue", search);

        verify(repository).findById(search);
    }

    @Test
    void shouldReturnSizeProvidedByRepository() {
        // given
        final long size = 123456789L;

        doReturn(size).when(repository).count();

        // when
        final long result = readerUnderTest.getTotalSize();

        // then
        assertThat(result).isEqualTo(size);
    }

    @Test
    void shouldReturnValidDataPage() {
        // given
        final int page = 1;
        final int size = 10;

        final var entities = Stream.generate(() -> testEntity).limit(size).collect(toList());

        doReturn(pageResponse).when(repository).findAll(any(Pageable.class));
        doReturn(entities).when(pageResponse).toList();
        doReturn(testDto).when(converter).entityToDto(testEntity);

        // when
        final var result = readerUnderTest.getList(page, size);

        // then
        assertThat(result)
                .isNotNull()
                .hasSize(size);

        verify(converter, times(size)).entityToDto(testEntity);
    }
}
