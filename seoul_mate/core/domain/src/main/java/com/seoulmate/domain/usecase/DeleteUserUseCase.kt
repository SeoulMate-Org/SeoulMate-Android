package com.seoulmate.domain.usecase

import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.dto.user.DeleteUserDto
import com.seoulmate.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        userId: Int,
    ): Flow<CommonDto<DeleteUserDto?>> = userRepository.deleteUser(
        userId = userId,
    )
}