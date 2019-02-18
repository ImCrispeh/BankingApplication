const initialState = {
    count: 0,
    history: []
};

const countReducer = (state = initialState, action) => {
    switch(action.type) {
        case 'INCREMENT':
            return Object.assign({}, state,
                {
                    count: state.count + 1,
                    history: [...state.history, 1]
                });
        case 'DECREMENT':
            return Object.assign({}, state,
                {
                    count: state.count - 1,
                    history: [...state.history, -1]
                });
        case 'RESET':
            return Object.assign({}, state, {count: 0, history: []});
        default:
            return state;
    }
}

export default countReducer;