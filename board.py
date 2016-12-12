import time

from enums import *
from min_max import min_max
from min_max_alpha_beta import min_max_alpha_beta


class State:
    def __init__(self):
        self.matrix = []
        self.turn = MatrixValues.player_1
        self.last_index = None

    def init_state(self):
        for i in range(12):
            for j in range(12):
                if (i, j) in [(5, 5), (6, 6)]:
                    self.matrix.append(MatrixValues.player_1)
                elif (i, j) in [(5,6), (6, 5)]:
                    self.matrix.append(MatrixValues.player_2)
                else:
                    self.matrix.append(MatrixValues.empty)
        self.turn = MatrixValues.player_1
        return self
		
    def get_opponent(self):
        return MatrixValues.player_1 if self.turn == MatrixValues.player_2 else MatrixValues.player_2

class Board:
    def __init__(self, heuristic_funcion=None, heuristic_funcion2=None):
        self.state = State().init_state()
        self.heuristic_funcion = heuristic_funcion
        self.heuristic_funcion2 = heuristic_funcion2
        self.max_depth = 3
        
    def reset(self, ui):
        self.state = State().init_state()
        ui.wait_for_player = False
        ui.winner = None
        self.turn_loop(ui)

    @staticmethod
    def clear_neighbors(state):
        for i in range(144):
            if state.matrix[i] == MatrixValues.neighbor:
                state.matrix[i] = MatrixValues.empty

    @staticmethod
    def get_neighbors(state):
        neighbors = set()
        for i in range(144):
            # find a 'stone' of the current player
            if state.matrix[i] == state.turn:
                
                # merge the neighbors of this 'stone' with the one we have found thus far
                neighbors |= Board._get_neighbors_indexes(state, i)
        return neighbors

    @staticmethod
    def _get_neighbors_indexes(state, index):
        neighbors = set()
        opponent = state.get_opponent()
 
        # search up
        if index >= 12:
            pointer = index - 12
            if state.matrix[pointer] == opponent:
                while pointer >= 0:
                    if state.matrix[pointer] != opponent:
                        break
                    pointer -= 12
                if pointer >= 0 and state.matrix[pointer] == MatrixValues.empty:
                    neighbors.add(pointer)
        
        # search down
        if index < 132:
            pointer = index + 12
            if state.matrix[pointer] == opponent:
                while pointer < 144:
                    if state.matrix[pointer] != opponent:
                        break
                    pointer += 12
                if pointer < 144 and state.matrix[pointer] == MatrixValues.empty:
                    neighbors.add(pointer)
        
        # search right
        if index % 12 != 11:
            pointer = index + 1
            if state.matrix[pointer] == opponent:
                while pointer % 12 != 0:
                    if state.matrix[pointer] != opponent:
                        break
                    pointer += 1
                if pointer % 12 != 0 and state.matrix[pointer] == MatrixValues.empty:
                    neighbors.add(pointer)
        
        # search left
        if index % 12 != 0:
            pointer = index - 1
            if state.matrix[pointer] == opponent:
                while pointer % 12 != 11:
                    if state.matrix[pointer] != opponent:
                        break
                    pointer -= 1
                if pointer % 12 != 11 and state.matrix[pointer] == MatrixValues.empty:
                    neighbors.add(pointer)
        
        # search up-left
        if index % 12 != 0 and index >= 12:
            pointer = index - 1 - 12
            if state.matrix[pointer] == opponent:
                while pointer % 12 != 11 and pointer >= 0:
                    if state.matrix[pointer] != opponent:
                        break
                    pointer = pointer - 1 - 12
                if pointer % 12 != 11 and pointer >= 0 and state.matrix[pointer] == MatrixValues.empty:
                    neighbors.add(pointer)
        
        # search up-right
        if index % 12 != 11 and index >= 12:
            pointer = index + 1 - 12
            if state.matrix[pointer] == opponent:
                while pointer % 12 != 0 and pointer >= 0:
                    if state.matrix[pointer] != opponent:
                        break
                    pointer = pointer + 1 - 12
                if pointer % 12 != 0 and pointer >= 0 and state.matrix[pointer] == MatrixValues.empty:
                    neighbors.add(pointer)
        
        # search down-left
        if index % 12 != 0 and index < 132:
            pointer = index - 1 + 12
            if state.matrix[pointer] == opponent:
                while pointer % 12 != 11 and pointer < 144:
                    if state.matrix[pointer] != opponent:
                        break
                    pointer = pointer - 1 + 12
                if pointer % 12 != 11 and pointer < 144 and state.matrix[pointer] == MatrixValues.empty:
                    neighbors.add(pointer)
        
        # search down-right
        if index % 12 != 11 and index < 132:
            pointer = index + 1 + 12
            if state.matrix[pointer] == opponent:
                while pointer % 12 != 0 and pointer < 144:
                    if state.matrix[pointer] != opponent:
                        break
                    pointer = pointer + 1 + 12
                if pointer % 12 != 0 and pointer < 144 and state.matrix[pointer] == MatrixValues.empty:
                    neighbors.add(pointer)
        
        return neighbors

    def get_neighbors_states(self):
        neighbors = Board.get_neighbors(self.state)
        
        # mark all of the neighbors
        for neighbor in neighbors:
            self.state.matrix[neighbor] = MatrixValues.neighbor
			
        print('neighbors', neighbors)
        boards = []
        
        for neighbor in neighbors:
            board = Board()
            board.state.matrix = self.state.matrix.copy()
            board.state.turn = self.state.turn
            board.state.last_index = neighbor
            
            opponents = Board._get_opponents_to_fill(board.state, neighbor)
            own_value = self.state.turn
            for opponent in opponents:
                board.state.matrix[opponent] = own_value
            board.state.matrix[neighbor] = own_value  
            board.next_turn()
            boards.append(board)
            
        
        return boards
 
    @staticmethod 
    def _get_opponents_to_fill(state, index):
        own_value = state.turn
        opponent = state.get_opponent()
        opponents = []
        
        # fill up
        up_points = []
        if index >= 12:
            pointer = index - 12
            if state.matrix[pointer] == opponent:
                while pointer >= 0:
                    if state.matrix[pointer] == opponent:
                        up_points.append(pointer)
                    elif state.matrix[pointer] == own_value:
                        for point in up_points:
                            opponents.append(point)
                        break
                    else:
                        break
                    pointer -= 12
        
        # fill down
        down_points = []
        if index < 132:
            pointer = index + 12
            if state.matrix[pointer] == opponent:
                while pointer < 144:
                    if state.matrix[pointer] == opponent:
                        down_points.append(pointer)
                    elif state.matrix[pointer] == own_value:
                        for point in down_points:
                            opponents.append(point)
                        break
                    else:
                        break
                    pointer += 12
        
        # fill right
        right_points = []
        if index % 12 != 11:
            pointer = index + 1
            if state.matrix[pointer] == opponent:
                while pointer % 12 != 0:
                    if state.matrix[pointer] == opponent:
                        right_points.append(pointer)
                    elif state.matrix[pointer] == own_value:
                        for point in right_points:
                            opponents.append(point)
                        break
                    else:
                        break
                    pointer += 1
        
        # fill left
        left_points = []
        if index % 12 != 0:
            pointer = index - 1
            if state.matrix[pointer] == opponent:
                while pointer % 12 != 11:
                    if state.matrix[pointer] == opponent:
                        left_points.append(pointer)
                    elif state.matrix[pointer] == own_value:
                        for point in left_points:
                            opponents.append(point)
                        break
                    else:
                        break
                    pointer -= 1
        
        # fill up-left
        up_left_points = []
        if index % 12 != 0 and index >= 12:
            pointer = index - 1 - 12
            if state.matrix[pointer] == opponent:
                while pointer % 12 != 11 and pointer >= 0:
                    if state.matrix[pointer] == opponent:
                        up_left_points.append(pointer)
                    elif state.matrix[pointer] == own_value:
                        for point in up_left_points:
                            opponents.append(point)
                        break
                    else:
                        break
                    pointer = pointer - 1 - 12
        
        # fill up-right
        up_right_points = []
        if index % 12 != 11 and index >= 12:
            pointer = index + 1 - 12
            if state.matrix[pointer] == opponent:
                while pointer % 12 != 0 and pointer >= 0:
                    if state.matrix[pointer] == opponent:
                        up_right_points.append(pointer)
                    elif state.matrix[pointer] == own_value:
                        for point in up_right_points:
                            opponents.append(point)
                        break
                    else:
                        break
                    pointer = pointer + 1 - 12
        
        # fill down-left
        down_left_points = []
        if index % 12 != 0 and index < 132:
            pointer = index - 1 + 12
            if state.matrix[pointer] == opponent:
                while pointer % 12 != 11 and pointer < 144:
                    if state.matrix[pointer] == opponent:
                        down_left_points.append(pointer)
                    elif state.matrix[pointer] == own_value:
                        for point in down_left_points:
                            opponents.append(point)
                        break
                    else:
                        break
                    pointer = pointer - 1 + 12
        
        # fill down-right
        down_right_points = []
        if index % 12 != 11 and index < 132:
            pointer = index + 1 + 12
            if state.matrix[pointer] == opponent:
                while pointer % 12 != 0 and pointer < 144:
                    if state.matrix[pointer] == opponent:
                        down_right_points.append(pointer)
                    elif state.matrix[pointer] == own_value:
                        for point in down_right_points:
                            opponents.append(point)
                        break
                    else:
                        break
                    pointer = pointer + 1 + 12
        
        return opponents
        
    def player_action(self, index, ui):
        opponents = Board._get_opponents_to_fill(self.state, index)
        
        # fill the neighbor and opponents with your own value
        own_value = self.state.turn
        for opponent in opponents:
            self.state.matrix[opponent] = own_value
        self.state.matrix[index] = own_value
        self.state.last_index = index

        self.next_turn()
        Board.clear_neighbors(self.state)

    @staticmethod
    def get_winner(state):
        player_1_counter = 0
        player_2_counter = 0
        
        for i in range(144):
            if state.matrix[i] == MatrixValues.player_1:
                player_1_counter += 1
            elif state.matrix[i] == MatrixValues.player_2:
                player_2_counter += 1
        
        if player_1_counter == 0:
            # player 1 eliminated, player 2 wins
            return Winner.player_2
        elif player_2_counter == 0:
            # player 2 eliminated, player 1 wins
            return Winner.player_1
        elif player_1_counter + player_2_counter == 144:
            if player_1_counter > player_2_counter:
                # player 1 covered more board, player 1 wins
                return Winner.player_1
            elif player_2_counter > player_1_counter:
                # player 2 covered more board, player 2 wins
                return Winner.player_2
            else:
                # both players covered the same space, draw
                return Winner.draw

        # the match is not over
        return None

    @staticmethod
    def get_secured(state):
        secured = set()
        
        # top-left corner
        if state.matrix[0] == state.turn:
            secured.add(0)
            i = 1
            consecutive = True
            while i < 11 and consecutive:
                j = i
                line = set()
                full_line = True
                while j % 12 != 11 and full_line:
                    if state.matrix[j] == state.turn:
                        line.add(j)
                    else:
                        full_line = False
                    j = j - 1 + 12
                if full_line:
                    secured |= line
                else:
                    consecutive = False
                i += 1
            if consecutive:
                i = 11
                while i < 143 and consecutive:
                    j = i
                    line = set()
                    full_line = True
                    while j < 143 and full_line:
                        if state.matrix[j] == state.turn:
                            line.add(j)
                        else:
                            full_line = False
                        j = j - 1 + 12
                    if full_line:
                        secured |= line
                    else:
                        consecutive = False
                    i += 12
        
        # top-right corner
        if state.matrix[11] == state.turn:
            secured.add(11)
            i = 10
            consecutive = True
            while i > 0 and consecutive:
                j = i
                line = set()
                full_line = True
                while j % 12 != 0 and full_line:
                    if state.matrix[j] == state.turn:
                        line.add(j)
                    else:
                        full_line = False
                    j = j + 1 + 12
                if full_line:
                    secured |= line
                else:
                    consecutive = False
                i -= 1
            if consecutive:
                i = 0
                while i < 132 and consecutive:
                    j = i
                    line = set()
                    full_line = True
                    while j < 143 and full_line:
                        if state.matrix[j] == state.turn:
                            line.add(j)
                        else:
                            full_line = False
                        j = j + 1 + 12
                    if full_line:
                        secured |= line
                    else:
                        consecutive = False
                    i += 12
        
        # bottom-left corner
        if state.matrix[132] == state.turn:
            secured.add(132)
            i = 133
            consecutive = True
            while i < 143 and consecutive:
                j = i
                line = set()
                full_line = True
                while j % 12 != 11 and full_line:
                    if state.matrix[j] == state.turn:
                        line.add(j)
                    else:
                        full_line = False
                    j = j - 1 - 12
                if full_line:
                    secured |= line
                else:
                    consecutive = False
                i += 1
            if consecutive:
                i = 143
                while i > 11 and consecutive:
                    j = i
                    line = set()
                    full_line = True
                    while j < 143 and full_line:
                        if state.matrix[j] == state.turn:
                            line.add(j)
                        else:
                            full_line = False
                        j = j - 1 - 12
                    if full_line:
                        secured |= line
                    else:
                        consecutive = False
                    i -= 12
        
        # bottom-right corner
        if state.matrix[143] == state.turn:
            secured.add(143)
            i = 142
            consecutive = True
            while i > 132 and consecutive:
                j = i
                line = set()
                full_line = True
                while j % 12 != 0 and full_line:
                    if state.matrix[j] == state.turn:
                        line.add(j)
                    else:
                        full_line = False
                    j = j + 1 - 12
                if full_line:
                    secured |= line
                else:
                    consecutive = False
                i -= 1
            if consecutive:
                i = 132
                while i > 0 and consecutive:
                    j = i
                    line = set()
                    full_line = True
                    while j < 143 and full_line:
                        if state.matrix[j] == state.turn:
                            line.add(j)
                        else:
                            full_line = False
                        j = j + 1 - 12
                    if full_line:
                        secured |= line
                    else:
                        consecutive = False
                    i -= 12
        
        # top edge
        i = 0
        consecutive = True
        while i <= 132 and consecutive:
            line = set()
            full_line = True
            j = 0
            while j <= 11 and full_line:
                if state.matrix[i + j] == state.turn:
                    line.add(i + j)
                else:
                    full_line = False
                j += 1
            if full_line:
                secured |= line
            else:
                consecutive = False
            i += 12
        
        # bottom edge
        i = 132
        consecutive = True
        while i >= 0 and consecutive:
            line = set()
            full_line = True
            j = 0
            while j <= 11 and full_line:
                if state.matrix[i + j] == state.turn:
                    line.add(i + j)
                else:
                    full_line = False
                j += 1
            if full_line:
                secured |= line
            else:
                consecutive = False
            i -= 12
        
        # left edge
        i = 0
        consecutive = True
        while i <= 11 and consecutive:
            line = set()
            full_line = True
            j = 0
            while j <= 132 and full_line:
                if state.matrix[i + j] == state.turn:
                    line.add(i + j)
                else:
                    full_line = False
                j += 12
            if full_line:
                secured |= line
            else:
                consecutive = False
            i += 1
        
        # right edge
        i = 11
        consecutive = True
        while i >= 0 and consecutive:
            line = set()
            full_line = True
            j = 0
            while j <= 132 and full_line:
                if state.matrix[i + j] == state.turn:
                    line.add(i + j)
                else:
                    full_line = False
                j += 12
            if full_line:
                secured |= line
            else:
                consecutive = False
            i -= 1
        
        return len(secured)

    def turn_loop(self, ui):
        print()
        print('turn: ', self.state.turn)
        print()
        players = ui.players
        
        winner = Board.get_winner(self.state)
        if winner is not None:
            print('winner', winner)
            ui.winner = winner
            ui.draw_board()
            return

        neighbors = Board.get_neighbors(self.state)
        if len(neighbors) == 0:
            # no possible moves, skip turn
            ui.draw_board()
            self.next_turn()
            print('Skipping, call turn loop')
            self.turn_loop(ui)
            return

        if players[self.state.turn.value - 1] == PlayerOptions.Player:
            # it's a player's turn, mark his possible moves and wait for his action
            for neighbor in neighbors:
                self.state.matrix[neighbor] = MatrixValues.neighbor
            ui.wait_for_player = True
            
        elif players[self.state.turn.value - 1] == PlayerOptions.MinMax:
            if self.state.turn == MatrixValues.player_2 and players[0] == players[1]:
                # both players are AI, use 2nd heuristic funcion for the second player
                heuristic_funcion = self.heuristic_funcion2
            else:
                heuristic_funcion = self.heuristic_funcion
            
            # call minmax
            print('call minmax')
            current_turn =  self.state.turn
            best, best_path = min_max(self, 1, heuristic_funcion, self.max_depth + 1)
            print('returned from minmax')
            if len(best_path) > 0:
                # extract the next state to use
                self.state = best_path[-1].state
                self.state.turn = current_turn
            print('best', best)
            print('best_path', [b.state.last_index for b in best_path])
            
            self.next_turn()
            Board.clear_neighbors(self.state)

        elif players[self.state.turn.value - 1] == PlayerOptions.MinMaxAlphaBeta:
            if self.state.turn == MatrixValues.player_2 and players[0] == players[1]:
                # both players are AI, use 2nd heuristic funcion for the second player
                heuristic_funcion = self.heuristic_funcion2
            else:
                heuristic_funcion = self.heuristic_funcion

            # call MinMaxAlphaBeta
            print('call MinMaxAlphaBeta')
            current_turn =  self.state.turn
            best, best_path = min_max_alpha_beta(self, 1, heuristic_funcion, self.max_depth + 1, -float('inf'), float('inf'))
            print('returned from MinMaxAlphaBeta')
            if len(best_path) > 0:
                # extract the next state to use
                self.state = best_path[-1].state
                self.state.turn = current_turn
            print('best', best)
            print('best_path', [b.state.last_index for b in best_path])
            
            self.next_turn()
            Board.clear_neighbors(self.state)

        ui.draw_board()
        print(players[self.state.turn.value - 1])
        if not ui.wait_for_player:
            print('call turn loop')
            self.turn_loop(ui)

    def next_turn(self):
        self.state.turn = self.state.get_opponent()
		
