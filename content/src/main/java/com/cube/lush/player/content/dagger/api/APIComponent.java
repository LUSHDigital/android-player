package com.cube.lush.player.content.dagger.api;

import com.cube.lush.player.api.LushAPI;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.content.manager.SearchManager;
import com.cube.lush.player.content.repository.Repository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 23/03/2017.
 */
@Singleton
@Component(modules={APIModule.class})
public interface APIComponent
{
	// Provides the dependency
	LushAPI provideAPI();

	// Consumes the dependency
	void inject(MediaManager mediaManager);
	void inject(SearchManager searchManager);

	void inject(Repository repository);
}