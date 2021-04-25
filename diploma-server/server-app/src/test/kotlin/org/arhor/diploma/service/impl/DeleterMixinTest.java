//package org.arhor.diploma.service.impl;
//
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoInteractions;
//
//import java.util.Optional;
//
//import org.arhor.diploma.commons.Identifiable;
//import org.arhor.diploma.commons.data.EntityNotFoundException;
//import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject;
//import org.arhor.diploma.data.persistence.repository.BaseRepository;
//import org.arhor.diploma.testutils.RandomParameter;
//import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.platform.commons.annotation.Testable;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.mockito.quality.Strictness;
//
//import kotlin.jvm.JvmClassMappingKt;
//
//@Testable
//@ExtendWith({MockitoExtension.class, RandomParameter.Resolver.class})
//@MockitoSettings(strictness = Strictness.STRICT_STUBS)
//class DeleterMixinTest {
//
//    @Mock private BaseRepository<DeletableDomainObject<String>, String> repository;
//    @Mock private Identifiable<String> testDto;
//    @Mock private DeletableDomainObject<String> testEntity;
//
//    @InjectMocks
//    private DeleterMixin<DeletableDomainObject<String>, Identifiable<String>, String> deleterUnderTest;
//
//    @Test
//    void shouldThrowIllegalArgumentException() {
//        // given
//        doReturn(null).when(testDto).getId();
//
//        // when
//        ThrowingCallable action = () -> deleterUnderTest.delete(testDto);
//
//        // then
//        assertThatThrownBy(action)
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining(DeleterMixin.KEY_PROPERTY);
//
//        verifyNoInteractions(repository);
//    }
//
//    @Test
//    void shouldThrowEntityNotFoundException(@RandomParameter final String testId) {
//        // given
//        final var entityType = JvmClassMappingKt.getKotlinClass(String.class);
//
//        doReturn(testId).when(testDto).getId();
//        doReturn(Optional.empty()).when(repository).findById(testId);
//        doReturn(entityType).when(repository).getEntityType();
//
//        // when
//        ThrowingCallable action = () -> deleterUnderTest.delete(testDto);
//
//        // then
//        assertThatThrownBy(action)
//                .isInstanceOf(EntityNotFoundException.class)
//                .hasFieldOrPropertyWithValue("entityType", entityType.getSimpleName())
//                .hasFieldOrPropertyWithValue("propName", DeleterMixin.KEY_PROPERTY)
//                .hasFieldOrPropertyWithValue("propValue", testId);
//
//        verify(repository).findById(testId);
//    }
//
//    @Test
//    void shouldDeletePassedObjectWithoutException(@RandomParameter final String testId) {
//        // given
//        doReturn(testId).when(testDto).getId();
//        doReturn(Optional.of(testEntity)).when(repository).findById(testId);
//
//        // when
//        deleterUnderTest.delete(testDto);
//
//        // then
//        verify(repository).delete(testEntity);
//    }
//}
