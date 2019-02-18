import React from 'react';
import { connect } from 'react-redux';
import { increment, decrement, reset } from './actions';
import PropTypes from 'prop-types';

function Counter(props) {
    const history = props.history.map((number, index) => {
        const desc = number > 0 ? 'Incremented counter' : 'Decremented counter';
        return <li key={index}>{desc}</li>;
    });
    return (
        <div>
            <h3>Counter: {props.count}</h3>
            <div>
                <button onClick={props.onDecrementClick}>Decrement</button>
                <button onClick={props.onIncrementClick}>Increment</button>
                <button onClick={props.onResetClick}>Reset</button>
            </div>
            <div>
                <ul>{history}</ul>
            </div>
        </div>
    )
}

Counter.propTypes = {
    count: PropTypes.number.isRequired,
    onDecrementClick: PropTypes.func.isRequired,
    onIncrementClick: PropTypes.func.isRequired,
    onResetClick: PropTypes.func.isRequired
}

function mapStateToProps(state) {
    return {
        count: state.count,
        history: state.history
    };
}

function mapDispatchToProps(dispatch) {
    return {
        onIncrementClick: () => dispatch(increment()),
        onDecrementClick: () => dispatch(decrement()),
        onResetClick: () => dispatch(reset())
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(Counter);