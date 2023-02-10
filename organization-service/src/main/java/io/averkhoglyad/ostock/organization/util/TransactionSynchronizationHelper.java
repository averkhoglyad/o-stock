package io.averkhoglyad.ostock.organization.util;

import org.springframework.core.Ordered;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public abstract class TransactionSynchronizationHelper
{

	private TransactionSynchronizationHelper() {}

	public static void synchronizeTransaction( Consumer<Synchronization> register )
	{
		SynchronizationImpl sync = new SynchronizationImpl();
		register.accept( sync );
		sync.register();
	}

	public interface Synchronization
	{
		Synchronization order( int order );
		Synchronization suspend( Runnable cb );
		Synchronization resume( Runnable cb );
		Synchronization flush( Runnable cb );
		Synchronization beforeCommit( Consumer<Boolean> cb );
		Synchronization beforeCommit( Runnable cb );
		Synchronization beforeCompletion( Runnable cb );
		Synchronization afterCommit( Runnable cb );
		Synchronization afterCompletion( IntConsumer cb );
		Synchronization afterCompletion( Runnable cb );
		Synchronization afterSuccess( Runnable cb );
		Synchronization afterRollBack( Runnable cb );
	}

	private static class SynchronizationImpl implements Synchronization
	{
		private int order = Ordered.LOWEST_PRECEDENCE;
		private List<Runnable> suspend = new ArrayList<>();
		private List<Runnable> resume = new ArrayList<>();
		private List<Runnable> flush = new ArrayList<>();
		private List<Consumer<Boolean>> beforeCommit = new ArrayList<>();
		private List<Runnable> beforeCompletion = new ArrayList<>();
		private List<Runnable> afterCommit = new ArrayList<>();
		private List<IntConsumer> afterCompletion = new ArrayList<>();

		public void register()
		{
			TransactionSynchronization synchronization = new TransactionSynchronizationImpl(
			  order, suspend, resume, flush, beforeCommit, beforeCompletion, afterCommit, afterCompletion );
			TransactionSynchronizationManager.registerSynchronization( synchronization );
		}

		@Override
		public Synchronization order( int order )
		{
			this.order = order;
			return this;
		}

		@Override
		public Synchronization suspend( Runnable cb )
		{
			this.suspend.add( cb );
			return this;
		}

		@Override
		public Synchronization resume( Runnable cb )
		{
			this.resume.add( cb );
			return this;
		}

		@Override
		public Synchronization flush( Runnable cb )
		{
			this.flush.add( cb );
			return this;
		}

		@Override
		public Synchronization beforeCommit( Runnable cb )
		{
			return beforeCommit( _ignore -> cb.run() );
		}

		@Override
		public Synchronization beforeCommit( Consumer<Boolean> cb )
		{
			this.beforeCommit.add( cb );
			return this;
		}

		@Override
		public Synchronization beforeCompletion( Runnable cb )
		{
			this.beforeCompletion.add( cb );
			return this;
		}

		@Override
		public Synchronization afterCommit( Runnable cb )
		{
			this.afterCommit.add( cb );
			return this;
		}

		@Override
		public Synchronization afterCompletion( Runnable cb )
		{
			return this.afterCompletion( ( int it ) -> cb.run() );
		}

		@Override
		public Synchronization afterCompletion( IntConsumer cb )
		{
			this.afterCompletion.add( cb );
			return this;
		}

		@Override
		public Synchronization afterSuccess( Runnable cb )
		{
			this.afterCompletion.add( status -> {
				if( status == TransactionSynchronization.STATUS_COMMITTED )
				{
					cb.run();
				}
			} );
			return this;
		}

		@Override
		public Synchronization afterRollBack( Runnable cb )
		{
			this.afterCompletion.add( status -> {
				if( status == TransactionSynchronization.STATUS_ROLLED_BACK )
				{
					cb.run();
				}
			} );
			return this;
		}
	}

	private static class TransactionSynchronizationImpl implements TransactionSynchronization, Ordered
	{
		private final int order;
		private final List<Runnable> suspend;
		private final List<Runnable> resume;
		private final List<Runnable> flush;
		private final List<Consumer<Boolean>> beforeCommit;
		private final List<Runnable> beforeCompletion;
		private final List<Runnable> afterCommit;
		private final List<IntConsumer> afterCompletion;

		private TransactionSynchronizationImpl(
		  int order,
		  List<Runnable> suspend,
		  List<Runnable> resume,
		  List<Runnable> flush,
		  List<Consumer<Boolean>> beforeCommit,
		  List<Runnable> beforeCompletion,
		  List<Runnable> afterCommit,
		  List<IntConsumer> afterCompletion )
		{
			this.order = order;
			this.suspend = suspend;
			this.resume = resume;
			this.flush = flush;
			this.beforeCommit = beforeCommit;
			this.beforeCompletion = beforeCompletion;
			this.afterCommit = afterCommit;
			this.afterCompletion = afterCompletion;
		}

		@Override
		public int getOrder()
		{
			return order;
		}

		@Override
		public void suspend()
		{
			suspend.forEach( it -> it.run() );
		}

		@Override
		public void resume()
		{
			resume.forEach( it -> it.run() );
		}

		@Override
		public void flush()
		{
			flush.forEach( it -> it.run() );
		}

		@Override
		public void beforeCommit( boolean readOnly )
		{
			beforeCommit.forEach( it -> it.accept( readOnly ) );
		}

		@Override
		public void beforeCompletion()
		{
			beforeCompletion.forEach( it -> it.run() );
		}

		@Override
		public void afterCommit()
		{
			afterCommit.forEach( it -> it.run() );
		}

		@Override
		public void afterCompletion( int status )
		{
			afterCompletion.forEach( it -> it.accept( status ) );
		}
	}

}
