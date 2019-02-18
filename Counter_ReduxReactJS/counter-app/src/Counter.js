import React from 'react';
import { connect } from 'react-redux';
import { increment, decrement } from './actions';
import PropTypes from 'prop-types';

function Counter(props) {
    return (
        <div>
            <h3>Counter: {props.count}</h3>
            <div>
                <button onClick={props.onDecrementClick}>Decrement</button>
                <button onClick={props.onIncrementClick}>Increment</button>
            </div>
        </div>
    )
}

Counter.propTypes = {
    count: PropTypes.number.isRequired,
    onDecrementClick: PropTypes.func.isRequired,
    onIncrementClick: PropTypes.func.isRequired
}

function mapStateToProps(state) {
    return {
        count: state.count
    };
}

function mapDispatchToProps(dispatch) {
    return {
        onIncrementClick: () => dispatch(increment()),
        onDecrementClick: () => dispatch(decrement())
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(Counter);