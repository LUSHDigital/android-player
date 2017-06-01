package com.cube.lush.player.content.dagger.api;

import com.cube.lush.player.api.LushAPI;
import com.cube.lush.player.content.repository.Repository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger API component
 *
 * @author Jamie Cruwys
 */
@Singleton
@Component(modules={APIModule.class})
public interface APIComponent
{
	// Provides the dependency
	LushAPI provideAPI();

	// Consumes the dependency
	void inject(Repository repository);
}