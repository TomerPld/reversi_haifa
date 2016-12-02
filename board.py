from enum import IntEnum, Enum

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
    def __init__(self):
        self.init_state = State().init_state()
        self.state = self.init_state
        
    def reset(self):
        self.state = self.init_state

    def clear_neighbors(self):
        for i in range(144):
            if self.state.matrix[i] == MatrixValues.neighbor:
                self.state.matrix[i] = MatrixValues.empty
        
    def get_neighbors(self):
        for i in range(144):
            # find a 'stone' of the current player
            if self.state.matrix[i] == MatrixValues(self.state.turn + 1):
                print('own: ', i)
                self._get_neighbors_spots(i)

    def _get_neighbors_spots(self, index):
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
                    self.state.matrix[pointer] = MatrixValues.neighbor
        
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
                    self.state.matrix[pointer] = MatrixValues.neighbor
        
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
                    self.state.matrix[pointer] = MatrixValues.neighbor
        
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
                    self.state.matrix[pointer] = MatrixValues.neighbor
        
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
                    self.state.matrix[pointer] = MatrixValues.neighbor
        
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
                    self.state.matrix[pointer] = MatrixValues.neighbor
        
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
                    self.state.matrix[pointer] = MatrixValues.neighbor
        
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
                    self.state.matrix[pointer] = MatrixValues.neighbor
        

    def fill_neighbor(self, index):
        fill_value = MatrixValues(self.state.turn + 1)
        opponent = MatrixValues((1 - self.state.turn) + 1)
        self.state.matrix[index] = fill_value
        
        # fill up
        up_points = []
        if index >= 12:
            pointer = index - 12
            if self.state.matrix[pointer] == opponent:
                while pointer >= 0:
                    if self.state.matrix[pointer] == opponent:
                        up_points.append(pointer)
                    elif self.state.matrix[pointer] == fill_value:
                        for point in up_points:
                            self.state.matrix[point] = fill_value
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
                    elif self.state.matrix[pointer] == fill_value:
                        for point in down_points:
                            self.state.matrix[point] = fill_value
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
                    elif self.state.matrix[pointer] == fill_value:
                        for point in right_points:
                            self.state.matrix[point] = fill_value
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
                    elif self.state.matrix[pointer] == fill_value:
                        for point in left_points:
                            self.state.matrix[point] = fill_value
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
                    elif self.state.matrix[pointer] == fill_value:
                        for point in up_left_points:
                            self.state.matrix[point] = fill_value
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
                    elif self.state.matrix[pointer] == fill_value:
                        for point in up_right_points:
                            self.state.matrix[point] = fill_value
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
                    elif self.state.matrix[pointer] == fill_value:
                        for point in down_left_points:
                            self.state.matrix[point] = fill_value
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
                    elif self.state.matrix[pointer] == fill_value:
                        for point in down_right_points:
                            self.state.matrix[point] = fill_value
                        break
                    else:
                        break
                    pointer = pointer + 1 + 12
        
    def player_action(self, index, ui):
        self.fill_neighbor(index)
        self.next_turn()
        self.clear_neighbors()

    def turn_loop(self, ui):
        print()
        print('turn: ', self.state.turn)
        print('players: ', ui.players)
        print()
        print()
        players = ui.players
        self.get_neighbors()
        if players[self.state.turn] == PlayerOptions.Player:
            # it's a player's turn, mark his possible moves and wait for his action
            ui.wait_for_player = True
            
        elif players[self.state.turn] == PlayerOptions.MinMax:
            # call minmax
            pass
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
		
