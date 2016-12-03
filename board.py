from enum import IntEnum, Enum
from min_max import min_max
from min_max_alpha_beta import min_max_alpha_beta

class PlayerOptions(IntEnum):
    Player = 0
    MinMax = 1
    MinMaxAlphaBeta = 2

    def get_next(current):
        return PlayerOptions((current.value + 1) % len(PlayerOptions.__members__.items()))

class MatrixValues(Enum):
    empty = 0
    player_1 = 1
    player_2 = 2
    neighbor = 3
		
class State:
    def __init__(self):
        self.matrix = []
        self.turn = 0

    def init_state(self):
        for i in range(12):
            for j in range(12):
                if (i, j) in [(5, 5), (6, 6)]:
                    self.matrix.append(MatrixValues.player_1)
                elif (i, j) in [(5,6), (6, 5)]:
                    self.matrix.append(MatrixValues.player_2)
                else:
                    self.matrix.append(MatrixValues.empty)
        self.turn = 0
        return self

class Board:
    def __init__(self, heuristic_funcion=None):
        self.state = State().init_state()
        self.heuristic_funcion = heuristic_funcion
        self.max_depth = 3
        
    def reset(self):
        self.state = State().init_state()

    def clear_neighbors(self):
        for i in range(144):
            if self.state.matrix[i] == MatrixValues.neighbor:
                self.state.matrix[i] = MatrixValues.empty
        
    def get_neighbors(self):
        neighbors = set()
        for i in range(144):
            # find a 'stone' of the current player
            if self.state.matrix[i] == MatrixValues(self.state.turn + 1):
                print('own: ', i)
                
                # merge the neighbors of this 'stone' with the one we have found thus far
                neighbors |= self._get_neighbors_indexes(i)
        return neighbors

    def _get_neighbors_indexes(self, index):
        neighbors = set()
        opponent = MatrixValues.player_1 if self.state.turn == 1 else MatrixValues.player_2
 
        # search up
        if index >= 12:
            pointer = index - 12
            if self.state.matrix[pointer] == opponent:
                while pointer >= 0:
                    if self.state.matrix[pointer] != opponent:
                        break
                    pointer -= 12
                if pointer >= 0 and self.state.matrix[pointer] == MatrixValues.empty:
                    print('found ', pointer, 'searching up')
                    neighbors.add(pointer)
        
        # search down
        if index < 132:
            pointer = index + 12
            if self.state.matrix[pointer] == opponent:
                while pointer < 144:
                    if self.state.matrix[pointer] != opponent:
                        break
                    pointer += 12
                if pointer < 144 and self.state.matrix[pointer] == MatrixValues.empty:
                    print('found ', pointer, 'searching down')
                    neighbors.add(pointer)
        
        # search right
        if index % 12 != 11:
            pointer = index + 1
            if self.state.matrix[pointer] == opponent:
                while pointer % 12 != 0:
                    if self.state.matrix[pointer] != opponent:
                        break
                    pointer += 1
                if pointer % 12 != 0 and self.state.matrix[pointer] == MatrixValues.empty:
                    print('found ', pointer, 'searching right')
                    neighbors.add(pointer)
        
        # search left
        if index % 12 != 0:
            pointer = index - 1
            if self.state.matrix[pointer] == opponent:
                while pointer % 12 != 11:
                    if self.state.matrix[pointer] != opponent:
                        break
                    pointer -= 1
                if pointer % 12 != 11 and self.state.matrix[pointer] == MatrixValues.empty:
                    print('found ', pointer, 'searching left')
                    neighbors.add(pointer)
        
        # search up-left
        if index % 12 != 0 and index >= 12:
            pointer = index - 1 - 12
            if self.state.matrix[pointer] == opponent:
                while pointer % 12 != 11 and pointer >= 0:
                    if self.state.matrix[pointer] != opponent:
                        break
                    pointer = pointer - 1 - 12
                if pointer % 12 != 11 and pointer >= 0 and self.state.matrix[pointer] == MatrixValues.empty:
                    print('found ', pointer, 'searching up-left')
                    neighbors.add(pointer)
        
        # search up-right
        if index % 12 != 11 and index >= 12:
            pointer = index + 1 - 12
            if self.state.matrix[pointer] == opponent:
                while pointer % 12 != 0 and pointer >= 0:
                    if self.state.matrix[pointer] != opponent:
                        break
                    pointer = pointer + 1 - 12
                if pointer % 12 != 0 and pointer >= 0 and self.state.matrix[pointer] == MatrixValues.empty:
                    print('found ', pointer, 'searching up-right')
                    neighbors.add(pointer)
        
        # search down-left
        if index % 12 != 0 and index < 132:
            pointer = index - 1 + 12
            if self.state.matrix[pointer] == opponent:
                while pointer % 12 != 11 and pointer < 144:
                    if self.state.matrix[pointer] != opponent:
                        break
                    pointer = pointer - 1 + 12
                if pointer % 12 != 11 and pointer < 144 and self.state.matrix[pointer] == MatrixValues.empty:
                    print('found ', pointer, 'searching down-left')
                    neighbors.add(pointer)
        
        # search down-right
        if index % 12 != 11 and index < 132:
            pointer = index + 1 - 12
            if self.state.matrix[pointer] == opponent:
                while pointer % 12 != 0 and pointer < 144:
                    if self.state.matrix[pointer] != opponent:
                        break
                    pointer = pointer + 1 + 12
                if pointer % 12 != 0 and pointer < 144 and self.state.matrix[pointer] == MatrixValues.empty:
                    print('found ', pointer, 'searching down-right')
                    neighbors.add(pointer)
        
        return neighbors

    def get_neighbors_states(self):
        neighbors = self.get_neighbors()
        states = []
        board = Board()
        
        for neighbor in neighbors:
            board.state = State()
            board.state.matrix = self.state.matrix.copy()
            board.state.turn = self.state.turn
            
            opponents = board._get_opponents_to_fill(neighbor)
            own_value = MatrixValues(self.state.turn + 1)
            for opponent in opponents:
                board.state.matrix[opponent] = own_value
            board.state.matrix[neighbor] = own_value  
            board.next_turn()
            states.append(board.state)
            
        
        return states
 
    def _get_opponents_to_fill(self, index):
        own_value = MatrixValues(self.state.turn + 1)
        opponent = MatrixValues((1 - self.state.turn) + 1)
        opponents = []
        
        # fill up
        up_points = []
        if index >= 12:
            pointer = index - 12
            if self.state.matrix[pointer] == opponent:
                while pointer >= 0:
                    if self.state.matrix[pointer] == opponent:
                        up_points.append(pointer)
                    elif self.state.matrix[pointer] == own_value:
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
            if self.state.matrix[pointer] == opponent:
                while pointer < 144:
                    if self.state.matrix[pointer] == opponent:
                        down_points.append(pointer)
                    elif self.state.matrix[pointer] == own_value:
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
            if self.state.matrix[pointer] == opponent:
                while pointer % 12 != 0:
                    if self.state.matrix[pointer] == opponent:
                        right_points.append(pointer)
                    elif self.state.matrix[pointer] == own_value:
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
            if self.state.matrix[pointer] == opponent:
                while pointer % 12 != 11:
                    if self.state.matrix[pointer] == opponent:
                        left_points.append(pointer)
                    elif self.state.matrix[pointer] == own_value:
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
            if self.state.matrix[pointer] == opponent:
                while pointer % 12 != 11 and index >= 0:
                    if self.state.matrix[pointer] == opponent:
                        up_left_points.append(pointer)
                    elif self.state.matrix[pointer] == own_value:
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
            if self.state.matrix[pointer] == opponent:
                while pointer % 12 != 0 and index >= 0:
                    if self.state.matrix[pointer] == opponent:
                        up_right_points.append(pointer)
                    elif self.state.matrix[pointer] == own_value:
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
            if self.state.matrix[pointer] == opponent:
                while pointer % 12 != 11 and index < 144:
                    if self.state.matrix[pointer] == opponent:
                        down_left_points.append(pointer)
                    elif self.state.matrix[pointer] == own_value:
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
            if self.state.matrix[pointer] == opponent:
                while pointer % 12 != 0 and index < 144:
                    if self.state.matrix[pointer] == opponent:
                        down_right_points.append(pointer)
                    elif self.state.matrix[pointer] == own_value:
                        for point in down_right_points:
                            opponents.append(point)
                        break
                    else:
                        break
                    pointer = pointer + 1 + 12
        
        return opponents
        
    def player_action(self, index, ui):
        opponents = self._get_opponents_to_fill(index)
        
        # fill the neighbor and opponents with your own value
        own_value = MatrixValues(self.state.turn + 1)
        for opponent in opponents:
            self.state.matrix[opponent] = own_value
        self.state.matrix[index] = own_value

        self.next_turn()
        self.clear_neighbors()

    def turn_loop(self, ui):
        print()
        print('turn: ', self.state.turn)
        print('players: ', ui.players)
        print()
        print()
        players = ui.players
        neighbors = self.get_neighbors()
        
        # mark all of the neighbors
        for neighbor in neighbors:
            self.state.matrix[neighbor] = MatrixValues.neighbor

        if players[self.state.turn] == PlayerOptions.Player:
            # it's a player's turn, mark his possible moves and wait for his action
            ui.wait_for_player = True
            
        elif players[self.state.turn] == PlayerOptions.MinMax:
            # call minmax
            print('call minmax')
            best, best_path = min_max(self.state, 1, self.heuristic_funcion, self.max_depth, self)
            if len(best_path) > 0:
                # extract the next state to use
                self.state = best_path[-1]
            print('best', best)
            
            self.next_turn()
            self.clear_neighbors()
        elif players[self.state.turn] == PlayerOptions.MinMaxAlphaBeta:
            # call MinMaxAlphaBeta
            pass
            self.next_turn()
            self.clear_neighbors()

        ui.draw_board()
    
    def set_state(self, state):
        self.state = state

    def next_turn(self):
        self.state.turn = 1 - self.state.turn
		
