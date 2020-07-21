/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.test.espresso.IdlingResource;

import com.example.android.architecture.blueprints.todoapp.Injection;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.statistics.StatisticsActivity;
import com.example.android.architecture.blueprints.todoapp.util.ActivityUtils;
import com.example.android.architecture.blueprints.todoapp.util.EspressoIdlingResource;
import com.example.android.architecture.blueprints.todoapp.util.LogUtil;
import com.google.android.material.navigation.NavigationView;

/**
 * 入口
 * MVP架构，
 * M：除去这个外的所有。
 * V：每个fragment
 * P：后缀为persenter的
 *
 * activity基本上是一种连接，
 * IP 为只在IV回调的接口
 * 整个view层，都只在P的控制。
 *
 * 结构上来说，应该是比较清晰的。但是确实也不是特别容易理解。
 *
 *
 * 修改：
 * MVP架构
 * M:基本是指TasksRepository，进行的一些列数据处理均通过这个
 * V：每个fragment
 * P：后缀为persenter的
 * 其他：静态工具方法，当成以上每个的一部分。或者分发处理。
 *
 * 结构中最不清晰的一点是，各种activity的入口太乱了。
 *
 * 懂了之后，感觉结构真的很柔顺，并且增加相应的功能，只要修改对应的一些MVP即可，
 * 但是由于model层与各方各面都耦合，所以，如果有需要修改model层，可能会造成比较大的麻烦，
 * 让人感觉model层其实是连接所有的核心，
 * 第二点是，连接各个MVP结构的入口可能会造成混乱。
 * 第三点是：大量的PV连接代码。
 * 但是解耦了view。
 * 并且方便单元测试。
 * 为了兼顾大框架，导致需要大量的结构代码。
 *
 * 大项目可以使用，或成为主流可以使用。
 *
 */
public class TasksActivity extends AppCompatActivity {

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private DrawerLayout mDrawerLayout;

    private TasksPresenter mTasksPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_act);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        TasksFragment tasksFragment =
                (TasksFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (tasksFragment == null) {
            // Create the fragment
            tasksFragment = TasksFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), tasksFragment, R.id.contentFrame);
            LogUtil.d("newInstanceTasksFragment");
        }

        // Create the presenter
        mTasksPresenter = new TasksPresenter(
                Injection.provideTasksRepository(getApplicationContext()), tasksFragment);

        // Load previously saved state, if available.
        if (savedInstanceState != null) {
            TasksFilterType currentFiltering =
                    (TasksFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            mTasksPresenter.setFiltering(currentFiltering);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY, mTasksPresenter.getFiltering());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                // Do nothing, we're already on that screen
                                break;
                            case R.id.statistics_navigation_menu_item:
                                Intent intent =
                                        new Intent(TasksActivity.this, StatisticsActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
