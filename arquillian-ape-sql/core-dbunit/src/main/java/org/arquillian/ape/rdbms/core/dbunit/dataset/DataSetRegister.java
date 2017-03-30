/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.arquillian.ape.rdbms.core.dbunit.dataset;

import org.dbunit.dataset.IDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stores data sets used to seed database and to verify
 * database state after test execution.
 *
 * @author <a href="mailto:bartosz.majsak@gmail.com">Bartosz Majsak</a>
 */
public class DataSetRegister {

    private final List<IDataSet> initial = new ArrayList<IDataSet>();

    private final List<IDataSet> expected = new ArrayList<IDataSet>();

    public void addInitial(IDataSet initial) {
        this.initial.add(initial);
    }

    public void addExpected(IDataSet expected) {
        this.expected.add(expected);
    }

    public List<IDataSet> getInitial() {
        return Collections.unmodifiableList(initial);
    }

    public List<IDataSet> getExpected() {
        return Collections.unmodifiableList(expected);
    }

}