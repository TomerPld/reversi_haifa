from board import *
from enums import *


def heuristic_funcion(board, depth):
    win = 1000000000
    loss = -win
    edge = 25
    corner = 125
    own_counter = 0
    opponent_counter = 0
    
    winner = Board.get_winner(board.state)
    if (winner == Winner.player_1 and board.state.turn == MatrixValues.player_1) or \
       (winner == Winner.player_2 and board.state.turn == MatrixValues.player_2):
        return win
    elif (winner == Winner.player_1 and board.state.turn == MatrixValues.player_2) or \
       (winner == Winner.player_2 and board.state.turn == MatrixValues.player_1):
        return loss

    
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
    
    return 2 * (own_counter - opponent_counter) + (own_possible - opponent_possible) + 25 * (own_secured - opponent_secured) + 5 * dominant_percentage + 10 * dominant_possible_percentage


def heuristic_funcion2(board, depth):
    win = 1000000000
    loss = -win
    edge = 25
    corner = 125
    own_counter = 0
    opponent_counter = 0
    
    winner = Board.get_winner(board.state)
    if (winner == Winner.player_1 and board.state.turn == MatrixValues.player_1) or \
       (winner == Winner.player_2 and board.state.turn == MatrixValues.player_2):
        return win
    elif (winner == Winner.player_1 and board.state.turn == MatrixValues.player_2) or \
       (winner == Winner.player_2 and board.state.turn == MatrixValues.player_1):
        return loss

    
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
    
    return 2 * (own_counter - opponent_counter) + (own_possible - opponent_possible) + 25 * (own_secured - opponent_secured) + 7 * dominant_percentage + 10 * dominant_possible_percentage
