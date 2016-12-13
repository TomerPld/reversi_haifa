from board import *
from enums import *

def heuristic_funcion(board, depth):
    win = float('inf')
    loss = -float('inf')
    edge = 5
    corner = 25
    own_counter = 0
    opponent_counter = 0
    
    for i in range(144):
        value = 1
        if i in [0, 11, 132, 143]:
            value = corner
        elif i % 12 in [0, 11] or i < 12 or i > 131:
            value = edge
    
        if board.state.matrix[i] == board.state.turn:
            own_counter += value
        elif board.state.matrix[i] == board.state.get_opponent():
            opponent_counter += value

    own_possible = len(Board.get_neighbors(board.state))
    board.state.turn = board.state.get_opponent()
    opponent_possible = len(Board.get_neighbors(board.state))
    board.state.turn = board.state.get_opponent()
    
    own_secured = Board.get_secured(board.state)
    board.state.turn = board.state.get_opponent()
    opponent_secured = Board.get_secured(board.state)
    board.state.turn = board.state.get_opponent()
    
    return 2 * (own_counter - opponent_counter) + (own_possible - opponent_possible) + 10 * (own_secured - opponent_secured)
	
def heuristic_funcion2(board, depth):
    win = float('inf')
    loss = -float('inf')
    edge = 25
    corner = 125
    own_counter = 0
    opponent_counter = 0
    
    for i in range(144):
        value = 1
        if i in [0, 11, 132, 143]:
            value = corner
        elif i % 12 in [0, 11] or i < 12 or i > 131:
            value = edge
    
        if board.state.matrix[i] == board.state.turn:
            own_counter += value
        elif board.state.matrix[i] == board.state.get_opponent():
            opponent_counter += value

    own_possible = len(Board.get_neighbors(board.state))
    board.state.turn = board.state.get_opponent()
    opponent_possible = len(Board.get_neighbors(board.state))
    board.state.turn = board.state.get_opponent()
    
    own_secured = Board.get_secured(board.state)
    board.state.turn = board.state.get_opponent()
    opponent_secured = Board.get_secured(board.state)
    board.state.turn = board.state.get_opponent()
    
    sum = own_counter + opponent_counter
    if own_counter > opponent_counter:
        dominant_percentage = (100 * own_counter) / sum
    elif opponent_counter > own_counter:
        dominant_percentage = (-100 * opponent_counter) / sum
    else:
        dominant_percentage = 0
    
    sum = own_possible + opponent_possible
    if own_possible > opponent_possible:
        dominant_possible_percentage = (100 * own_possible) / sum
    elif opponent_possible > own_possible:
        dominant_possible_percentage = (-100 * opponent_possible) / sum
    else:
        dominant_possible_percentage = 0
    
    return 2 * (own_counter - opponent_counter) + (own_possible - opponent_possible) + 100 * (own_secured - opponent_secured) + 10 * dominant_percentage + 10 * dominant_possible_percentage


def heuristic_funcion3(board, depth):
    p = 0
    c = 0
    l = 0
    m = 0
    d = 0
    own_counter = 0
    opponent_counter = 0
    own_corner_counter = 0
    opponent_corner_counter = 0
    for i in range(144):
        if i in [0, 11, 132, 143]:
            if board.state.matrix[i] == board.state.turn:
                own_corner_counter += 1
            elif board.state.matrix[i] == board.state.get_opponent():
                opponent_corner_counter += 1
        if board.state.matrix[i] == board.state.turn:
            own_counter += 1
        elif board.state.matrix[i] == board.state.get_opponent():
            opponent_counter += 1

    own_possible = len(Board.get_neighbors(board.state))
    board.state.turn = board.state.get_opponent()
    opponent_possible = len(Board.get_neighbors(board.state))
    board.state.turn = board.state.get_opponent()
    
    sum = own_counter + opponent_counter
    if own_counter > opponent_counter:
        p = (100 * own_counter) / sum
    elif opponent_counter > own_counter:
        p = (-100 * opponent_counter) / sum
    else:
        p = 0
    
    c = own_corner_counter - opponent_corner_counter
    
    sum = own_possible + opponent_possible
    if own_possible > opponent_possible:
        m = (100 * own_possible) / sum
    elif opponent_possible > own_possible:
        m = (-100 * opponent_possible) / sum
    else:
        m = 0
    
	# TODO: calc "close to corner" diff and assign to l
	
    d = own_counter - opponent_counter
    
    return -(-1 * (3 * p + 12 * c + -10 * l + 2 * d) + 10 * m)
