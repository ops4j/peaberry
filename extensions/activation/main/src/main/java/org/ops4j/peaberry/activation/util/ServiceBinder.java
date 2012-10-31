package org.ops4j.peaberry.activation.util;

import static com.google.inject.util.Types.newParameterizedType;

import java.util.Map;

import org.ops4j.peaberry.AttributeFilter;
import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Peaberry;
import org.ops4j.peaberry.ServiceRegistry;
import org.ops4j.peaberry.ServiceWatcher;
import org.ops4j.peaberry.builders.DecoratedServiceBuilder;
import org.ops4j.peaberry.builders.ExportProvider;
import org.ops4j.peaberry.builders.ImportDecorator;
import org.ops4j.peaberry.builders.InjectedServiceBuilder;
import org.ops4j.peaberry.builders.OutjectedServiceBuilder;
import org.ops4j.peaberry.builders.ProxyProvider;
import org.ops4j.peaberry.builders.QualifiedServiceBuilder;
import org.ops4j.peaberry.builders.ServiceBuilder;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

/**
 * Convenience binder used to avoid some repetitions that occur when using the
 * regular Peaberry DSL.
 * 
 * Instead of
 * <pre>
 * bind(Service.class).toProvider(service(Service.class).single());
 * </pre>
 * 
 * we have
 * 
 * <pre>
 * bindService(Service.class).single();
 * </pre>
 * 
 * @author rinsvind@gmail.com (Todor Boev)
 * 
 * @param <T>
 */
public class ServiceBinder<T> implements DecoratedServiceBuilder<T>, QualifiedServiceBuilder<T>,
    InjectedServiceBuilder<T>, OutjectedServiceBuilder<T> {

  private final Binder binder;
  private final Key<T> arg;
  private ServiceBuilder<T> service;

  public static <T> DecoratedServiceBuilder<T> service(Binder binder, Class<T> type) {
    return new ServiceBinder<T>(binder, Key.get(type));
  }

  public static <T> DecoratedServiceBuilder<T> service(Binder binder, TypeLiteral<T> type) {
    return new ServiceBinder<T>(binder, Key.get(type));
  }

  public static <T> DecoratedServiceBuilder<T> service(Binder binder, Key<T> type) {
    return new ServiceBinder<T>(binder, type);
  }

  private ServiceBinder(Binder binder, Key<T> arg) {
    this.binder = binder;
    this.arg = arg;
    this.service = Peaberry.service(arg);
  }

  public QualifiedServiceBuilder<T> decoratedWith(Key<? extends ImportDecorator<? super T>> key) {
    service = ((DecoratedServiceBuilder<T>) service).decoratedWith(key);
    return this;
  }

  public QualifiedServiceBuilder<T> decoratedWith(ImportDecorator<? super T> instance) {
    service = ((DecoratedServiceBuilder<T>) service).decoratedWith(instance);
    return this;
  }

  public InjectedServiceBuilder<T> attributes(Key<? extends Map<String, ?>> key) {
    service = ((QualifiedServiceBuilder<T>) service).attributes(key);
    return this;
  }

  public InjectedServiceBuilder<T> attributes(Map<String, ?> instance) {
    service = ((QualifiedServiceBuilder<T>) service).attributes(instance);
    return this;
  }

  public InjectedServiceBuilder<T> filter(Key<? extends AttributeFilter> key) {
    service = ((QualifiedServiceBuilder<T>) service).filter(key);
    return this;
  }

  public InjectedServiceBuilder<T> filter(AttributeFilter instance) {
    service = ((QualifiedServiceBuilder<T>) service).filter(instance);
    return this;
  }

  public OutjectedServiceBuilder<T> in(Key<? extends ServiceRegistry> key) {
    service = ((InjectedServiceBuilder<T>) service).in(key);
    return this;
  }

  public OutjectedServiceBuilder<T> in(ServiceRegistry instance) {
    service = ((InjectedServiceBuilder<T>) service).in(instance);
    return this;
  }

  public ServiceBuilder<T> out(Key<? extends ServiceWatcher<? super T>> key) {
    service = ((OutjectedServiceBuilder<T>) service).out(key);
    return this;
  }

  public ServiceBuilder<T> out(ServiceWatcher<? super T> instance) {
    service = ((OutjectedServiceBuilder<T>) service).out(instance);
    return this;
  }

  @SuppressWarnings("unchecked")
  public ExportProvider<T> export() {
    final TypeLiteral<Export<T>> tl = (TypeLiteral<Export<T>>) TypeLiteral.get(
        newParameterizedType(Export.class, arg.getTypeLiteral().getType()));
    binder.bind(tl).toProvider(service.export());

    /* We don't allow the provider to escape - we bound it already */
    return null;
  }

  @SuppressWarnings("unchecked")
  public ProxyProvider<Iterable<T>> multiple() {
    final TypeLiteral<Iterable<T>> tl = (TypeLiteral<Iterable<T>>) TypeLiteral.get(
        newParameterizedType(Iterable.class, arg.getTypeLiteral().getType()));
    binder.bind(tl).toProvider(service.multiple());

    /* We don't allow the provider to escape - we bound it already */
    return null;
  }

  public ProxyProvider<T> single() {
    binder.bind(arg).toProvider(service.single());

    /* We don't allow the provider to escape - we bound it already */
    return null;
  }
}
