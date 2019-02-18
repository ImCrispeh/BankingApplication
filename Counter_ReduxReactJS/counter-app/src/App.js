import React, { Component } from 'react';
import './App.css';
import Counter from './Counter';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
import countReducer from './reducer';

const store = createStore(countReducer);

class App extends Component {
    render() {
        return (
            <Provider store={store}>
                <div className="App">
                    <Counter />
                </div>
            </Provider>
        );
    }
}

export default App;
